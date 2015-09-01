package com.andaily.domain.user;

import java.util.Arrays;
import java.util.List;

/**
 * @author Shengzhao Li
 */

public enum Privilege {

    //Any user have the default privilege
    DEFAULT("Default"),

    CREATE_EDIT_INSTANCE("Create/Edit instance"),
    DELETE_INSTANCE("Delete instance"),
    START_STOP_INSTANCE("Start/Stop monitoring instance"),

    USER_MANAGEMENT("User management"),
    SYSTEM_SETTING("System setting");


    private String label;


    private Privilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return name();
    }

    public static List<Privilege> authPrivileges() {
        return Arrays.asList(
                CREATE_EDIT_INSTANCE,
                DELETE_INSTANCE,
                START_STOP_INSTANCE,
                USER_MANAGEMENT,
                SYSTEM_SETTING
        );
    }

    public static List<Privilege> registeredUserPrivileges() {
        return Arrays.asList(
                CREATE_EDIT_INSTANCE,
                DELETE_INSTANCE,
                START_STOP_INSTANCE
        );
    }
}