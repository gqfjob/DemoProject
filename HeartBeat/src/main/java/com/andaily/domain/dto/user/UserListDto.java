package com.andaily.domain.dto.user;

import com.andaily.domain.shared.paginated.DefaultPaginated;

import java.util.Map;

/**
 * @author Shengzhao Li
 */
public class UserListDto extends DefaultPaginated<UserDto> {


    private String username;


    public UserListDto() {
    }

    @Override
    public Map<String, Object> queryMap() {
        final Map<String, Object> map = super.queryMap();
        map.put("username", username);
        return map;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}