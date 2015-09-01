package com.andaily.service.operation.user;

import com.andaily.domain.dto.user.UserRegisterDto;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.user.Privilege;
import com.andaily.domain.user.User;
import com.andaily.domain.user.UserPrivilege;
import com.andaily.domain.user.UserRepository;
import com.andaily.infrastructure.ThreadLocalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public class RegisterUserHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterUserHandler.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
    private UserRegisterDto formDto;

    public RegisterUserHandler(UserRegisterDto formDto) {
        this.formDto = formDto;
    }

    public void handle() {
        User newUser = formDto.toDomain();
        userRepository.saveOrUpdate(newUser);

        addDefaultPrivileges(newUser);
        LOG.debug("[{}] register a new User: {}", ThreadLocalHolder.clientIp(), newUser);
    }

    private void addDefaultPrivileges(User newUser) {
        final List<Privilege> privileges = Privilege.registeredUserPrivileges();
        for (Privilege privilege : privileges) {
            UserPrivilege userPrivilege = new UserPrivilege(newUser, privilege);
            userRepository.saveOrUpdate(userPrivilege);
        }
    }
}