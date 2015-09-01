package com.andaily.domain.dto.user;

import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.user.User;
import com.andaily.infrastructure.PasswordHandler;

/**
 * @author Shengzhao Li
 */
public class UserRegisterDto extends AbstractDto {

    public static final String CAPTCHA_KEY = "captcha";


    private String username;
    private String password;
    private String rePassword;

    private String email;
    private String phone;

    public UserRegisterDto() {
    }


    public String getCaptchaKey() {
        return CAPTCHA_KEY;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User toDomain() {
        String encryptPass = PasswordHandler.encryptPassword(password);
        return new User(username, encryptPass, phone, email);
    }
}