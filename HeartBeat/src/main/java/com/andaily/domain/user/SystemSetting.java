package com.andaily.domain.user;

import com.andaily.domain.AbstractDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 15-4-14
 * <p/>
 * A global system setting,
 * it is singleton.
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "system_setting")
public class SystemSetting extends AbstractDomain {

    /**
     * Allow register or not. default is true(allow).
     * <p/>
     * A default register user have privileges as below:
     * DEFAULT,CREATE_EDIT_INSTANCE,DELETE_INSTANCE,START_STOP_INSTANCE
     * see {@link com.andaily.domain.user.Privilege#registeredUserPrivileges()}
     */
    @Column(name = "allow_user_register", columnDefinition = "tinyint(1)")
    private boolean allowUserRegister = true;


    public SystemSetting() {
    }

    public boolean allowUserRegister() {
        return allowUserRegister;
    }

    public SystemSetting allowUserRegister(boolean allowUserRegister) {
        this.allowUserRegister = allowUserRegister;
        return this;
    }
}
