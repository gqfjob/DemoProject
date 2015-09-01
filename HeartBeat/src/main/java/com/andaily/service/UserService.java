package com.andaily.service;

import com.andaily.domain.dto.user.*;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserListDto loadUserListDto(UserListDto listDto);

    UserFormDto loadUserFormDto(String guid);

    boolean isExistUsername(String username);

    void persistUserFormDto(UserFormDto formDto);

    void deleteUser(String guid);

    ResetUserPasswordDto resetUserPassword(String guid);

    void updateUserProfile(UserProfileDto profileDto);

    SystemSettingDto loadSystemSettingDto();

    void updateSystemSetting(SystemSettingDto settingDto);

    void registerUser(UserRegisterDto formDto);
}