package com.andaily.domain.dto.user;

import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.user.User;

/**
 * @author Shengzhao Li
 */
public class ResetUserPasswordDto extends AbstractDto {

    private String newPassword;
    private String username;

    public ResetUserPasswordDto() {
    }

    public ResetUserPasswordDto(User user, String newPassword) {
        super(user.guid());
        this.newPassword = newPassword;
        this.username = user.username();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}