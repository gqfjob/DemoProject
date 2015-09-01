package com.andaily.web.validator;

import org.springframework.validation.Errors;

/**
 * @author Shengzhao Li
 */
public abstract class AbstractPasswordValidator {

    protected static final int MIN_PASSWORD_LENGTH = 6;


    protected void validatePassword(String password, String rePassword, Errors errors) {

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            errors.rejectValue(passwordField(), null, "Password length >= 6");
            return;
        }

        if (!password.equals(rePassword)) {
            errors.rejectValue(rePasswordField(), null, "Check the Re-Password");
        }
    }


    protected String rePasswordField() {
        return "rePassword";
    }

    protected String passwordField() {
        return "password";
    }

}