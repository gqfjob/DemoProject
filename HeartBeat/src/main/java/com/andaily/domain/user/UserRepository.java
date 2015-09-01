package com.andaily.domain.user;

import com.andaily.domain.shared.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findByUsername(String username);

    List<Privilege> findUserPrivileges(User user);

    List<User> findListUsers(Map<String, Object> map);

    int totalListUsers(Map<String, Object> map);

    User findByUsernameIgnoreArchived(String username);

    int deleteUserPrivileges(User user);

    //Just one instance
    //If not found,will return default
    SystemSetting findSystemSetting();
}