package com.andaily.service.operation.user;

import com.andaily.domain.dto.user.SystemSettingDto;
import com.andaily.domain.shared.Application;
import com.andaily.domain.shared.BeanProvider;
import com.andaily.domain.shared.security.SecurityUtils;
import com.andaily.domain.user.SystemSetting;
import com.andaily.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 15-4-14
 *
 * @author Shengzhao Li
 */
public class SystemSettingUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemSettingUpdater.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
    private SystemSettingDto settingDto;

    public SystemSettingUpdater(SystemSettingDto settingDto) {
        this.settingDto = settingDto;
    }

    public void update() {
        final SystemSetting setting = userRepository.findSystemSetting();

        setting.allowUserRegister(settingDto.isAllowUserRegister());
        //Be sure call saveOrUpdate
        userRepository.saveOrUpdate(setting);
        LOGGER.debug("<{}> update system setting: {}", SecurityUtils.currentUsername(), setting);

        //update global setting
        Application.systemSetting().allowUserRegister(setting.allowUserRegister());
    }
}
