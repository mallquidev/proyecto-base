package com.mllq.base.proyecto_base.core.commons.models.enums;

import java.util.Set;

public enum Role {
    SUPPER_ADMIN,
    DRIVER,
    ADMIN,
    SUPPORT,
    ADMIN_TENANT, USER;

    public static Set<Role> getDefaultRoles() {
        return Set.of(
                Role.SUPPER_ADMIN,
                Role.DRIVER,
                Role.ADMIN,
                Role.SUPPORT,
                Role.ADMIN_TENANT,
                Role.USER
        );
    }
}
