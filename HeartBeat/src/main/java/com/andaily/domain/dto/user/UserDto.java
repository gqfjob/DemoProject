package com.andaily.domain.dto.user;

import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.user.Privilege;
import com.andaily.domain.user.User;
import com.andaily.infrastructure.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class UserDto extends AbstractDto {

    protected String username;
    protected String createDate;

    protected String phone;
    protected String email;
    protected boolean defaultUser;

    protected List<Privilege> privileges = new ArrayList<>();
    protected boolean registerUser;

    public UserDto() {
    }

    public UserDto(User user) {
        super(user.guid());
        this.username = user.username();
        this.createDate = DateUtils.toDateText(user.createTime());

        this.phone = user.phone();
        this.email = user.email();
        this.defaultUser = user.defaultUser();

        this.privileges = user.privileges();
        this.registerUser = user.registerUser();
    }

    public boolean isRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(boolean registerUser) {
        this.registerUser = registerUser;
    }

    public boolean isDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(boolean defaultUser) {
        this.defaultUser = defaultUser;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<UserDto> toDtos(List<User> users) {
        List<UserDto> dtoList = new ArrayList<>(users.size());
        for (User user : users) {
            dtoList.add(new UserDto(user));
        }
        return dtoList;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UserDto");
        sb.append("{username='").append(username).append('\'');
        sb.append(", createDate='").append(createDate).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", defaultUser=").append(defaultUser);
        sb.append('}');
        return sb.toString();
    }
}