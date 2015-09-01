package com.andaily.web.validator;

import com.andaily.domain.dto.user.UserProfileDto;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.infrastructure.PasswordHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Shengzhao Li
 */
@Component
public class UserProfileDtoValidator extends AbstractPasswordValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return UserProfileDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileDto formDto = (UserProfileDto) target;

        validateOldPassword(formDto, errors);
        validatePassword(formDto.getPassword(), formDto.getRePassword(), errors);

    }

    private void validateOldPassword(UserProfileDto formDto, Errors errors) {
        final String oldPassword = formDto.getOldPassword();
        if (StringUtils.isEmpty(oldPassword)) {
            errors.rejectValue("oldPassword", null, "Old password is required");
        }

        final String encryptPass = PasswordHandler.encryptPassword(oldPassword);
        final String password = SecurityUtils.currentUser().password();
        if (!encryptPass.equals(password)) {
            errors.rejectValue("oldPassword", null, "Wrong old password");
        }

    }


}