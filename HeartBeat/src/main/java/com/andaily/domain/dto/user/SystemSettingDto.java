package com.andaily.domain.dto.user;

import com.andaily.domain.dto.AbstractDto;
import com.andaily.domain.user.SystemSetting;

/**
 * 15-4-14
 *
 * @author Shengzhao Li
 */
public class SystemSettingDto extends AbstractDto {

    private boolean allowUserRegister;

    public SystemSettingDto() {
    }

    public SystemSettingDto(SystemSetting setting) {
        super(setting.guid());
        this.allowUserRegister = setting.allowUserRegister();
    }

    public boolean isAllowUserRegister() {
        return allowUserRegister;
    }

    public void setAllowUserRegister(boolean allowUserRegister) {
        this.allowUserRegister = allowUserRegister;
    }
}
