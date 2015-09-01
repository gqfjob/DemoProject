package com.andaily.domain.dto.user;

import com.andaily.domain.dto.AbstractDto;

/**
 * @author Shengzhao Li
 */
public class UserProfileDto extends AbstractDto {

    private String oldPassword;
    private String password;
    private String rePassword;

    public UserProfileDto() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}