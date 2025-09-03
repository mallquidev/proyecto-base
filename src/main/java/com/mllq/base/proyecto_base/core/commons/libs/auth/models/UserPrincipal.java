package com.mllq.base.proyecto_base.core.commons.libs.auth.models;

import com.mllq.base.proyecto_base.core.commons.models.enums.Role;
import com.mllq.base.proyecto_base.core.commons.models.enums.TimeZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal {
    private static final Logger log = LoggerFactory.getLogger(UserPrincipal.class);

    private Long id;
    private Long companyId;
    private String email;
    private UUID uuid;
    private TimeZone timeZone;
    private String ipAddress;
    private List<Role> roles;

    public void log() {
        log.info(this.toString());
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", email='" + email + '\'' +
                ", uuid=" + uuid +
                ", timeZone=" + timeZone +
                ", ipAddress='" + ipAddress + '\'' +
                ", roles=" + roles +
                '}';
    }
}
