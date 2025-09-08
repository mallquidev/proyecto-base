package com.mllq.base.proyect_base.core.domain.app.factory;

import com.mllq.base.proyect_base.core.commons.exception.ErrorCode;
import com.mllq.base.proyect_base.core.commons.exception.NotFound;

public class NotFoundException {

    public static NotFound userNotFound(Long id) {
        return  new NotFound("Usuario no encontrado con el id: " + id, ErrorCode.USER_NOT_FOUND);
    }
    public static NotFound userNotFoundByEmail(String email) {
        return new NotFound("Usuario no encontrado con el email: " + email, ErrorCode.USER_NOT_FOUND);
    }

}
