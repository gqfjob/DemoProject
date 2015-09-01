package com.andaily.service.impl;

import com.andaily.domain.dto.user.*;
import com.andaily.domain.shared.paginated.PaginatedLoader;
import com.andaily.domain.shared.security.AndailyUserDetails;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.domain.user.SystemSetting;
import com.andaily.domain.user.User;
import com.andaily.domain.user.UserRepository;
import com.andaily.infrastructure.MatchUtils;
import com.andaily.service.UserService;
import com.andaily.service.operation.user.RegisterUserHandler;
import com.andaily.service.operation.user.SystemSettingUpdater;
import com.andaily.service.operation.user.UserFormDtoPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
        }
        return new AndailyUserDetails(user);
    }


    @Override
    public UserListDto loadUserListDto(UserListDto listDto) {
        final Map<String, Object> map = listDto.queryMap();
        return listDto.load(new PaginatedLoader<UserDto>() {
            @Override
            public List<UserDto> loadList() {
                List<User> users = userRepository.findListUsers(map);
                return UserDto.toDtos(users);
            }

            @Override
            public int loadTotalSize() {
                return userRepository.totalListUsers(map);
            }
        });
    }

    @Override
    public UserFormDto loadUserFormDto(String guid) {
        if (MatchUtils.isCreate(guid)) {
            return new UserFormDto();
        } else {
            return new UserFormDto(userRepository.findByGuid(guid, User.class));
        }
    }

    @Override
    public boolean isExistUsername(String username) {
        User user = userRepository.findByUsernameIgnoreArchived(username);
        return user != null;
    }

    @Override
    public void persistUserFormDto(UserFormDto formDto) {
        UserFormDtoPersister persister = new UserFormDtoPersister(formDto);
        persister.persist();
    }

    @Override
    public void deleteUser(String guid) {
        User user = userRepository.findByGuid(guid, User.class);
        user.deleteMe();
    }

    @Override
    public ResetUserPasswordDto resetUserPassword(String guid) {
        User user = userRepository.findByGuid(guid, User.class);
        final String newPassword = user.resetPassword();
        return new ResetUserPasswordDto(user, newPassword);
    }

    @Override
    public void updateUserProfile(UserProfileDto profileDto) {
        User user = userRepository.findByGuid(SecurityUtils.currentUser().guid(), User.class);
        user.updatePassword(profileDto.getPassword());
    }

    @Override
    public SystemSettingDto loadSystemSettingDto() {
        final SystemSetting systemSetting = userRepository.findSystemSetting();
        return new SystemSettingDto(systemSetting);
    }

    @Override
    public void updateSystemSetting(SystemSettingDto settingDto) {
        SystemSettingUpdater updater = new SystemSettingUpdater(settingDto);
        updater.update();
    }

    @Override
    public void registerUser(UserRegisterDto formDto) {
        RegisterUserHandler registerUserHandler = new RegisterUserHandler(formDto);
        registerUserHandler.handle();
    }
}