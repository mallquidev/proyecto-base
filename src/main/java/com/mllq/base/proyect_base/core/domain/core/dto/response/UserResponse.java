package com.mllq.base.proyect_base.core.domain.core.dto.response;


import com.mllq.base.proyect_base.core.commons.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private Role role;
}



