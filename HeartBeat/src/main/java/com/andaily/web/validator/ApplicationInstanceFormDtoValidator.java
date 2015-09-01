package com.andaily.web.validator;

import com.andaily.domain.dto.application.ApplicationInstanceFormDto;
import com.andaily.infrastructure.MatchUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Shengzhao Li
 */
@Component
public class ApplicationInstanceFormDtoValidator implements Validator {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    @Override
    public boolean supports(Class<?> aClass) {
        return ApplicationInstanceFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "instanceName", null, "Instance name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "requestMethod", null, "Request method is required");

        ApplicationInstanceFormDto formDto = (ApplicationInstanceFormDto) target;
        validateMonitorUrl(formDto, errors);
        validateEmail(formDto, errors);

        validateMaxConnectionSeconds(formDto, errors);
    }

    private void validateMaxConnectionSeconds(ApplicationInstanceFormDto formDto, Errors errors) {
        final int maxConnectionSeconds = formDto.getMaxConnectionSeconds();
        final int seconds = formDto.getFrequency().getSeconds();

        if (maxConnectionSeconds > seconds || maxConnectionSeconds < 0) {
            errors.rejectValue("maxConnectionSeconds", null, "Max connection seconds must be positive number and less than frequency time: " + seconds);
        }
    }

    private void validateEmail(ApplicationInstanceFormDto formDto, Errors errors) {
        String email = formDto.getEmail();
        if (StringUtils.isEmpty(email)) {
            errors.rejectValue("email", null, "Email is required");
            return;
        }
        String[] emailArray;
        if (email.indexOf(";") > 0) {
            emailArray = email.split(";");
        } else {
            emailArray = new String[]{email};
        }

        for (String s : emailArray) {
            if (!MatchUtils.isEmail(s)) {
                errors.rejectValue("email", null, "Incorrect format for the email");
                break;
            }
        }
    }

    private void validateMonitorUrl(ApplicationInstanceFormDto formDto, Errors errors) {
        String monitorUrl = formDto.getMonitorUrl();
        if (StringUtils.isEmpty(monitorUrl)) {
            errors.rejectValue("monitorUrl", null, "URL is required");
            return;
        }

        String lowerUrl = monitorUrl.toLowerCase();
        if (!lowerUrl.startsWith(HTTP) && !lowerUrl.startsWith(HTTPS)) {
            errors.rejectValue("monitorUrl", null, "URL must start with 'http://' or 'https://'");
        }

    }


}