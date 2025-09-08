package com.mllq.base.proyect_base.core.domain.app.factory;

import com.mllq.base.proyect_base.core.commons.exception.BadRequest;
import com.mllq.base.proyect_base.core.commons.exception.ErrorCode;

public class UserException {

    public static BadRequest userAlreadyExistsByEmail(String email) {
        BadRequest badRequest = new BadRequest("El usuario con el correo " + email + " ya existe");
        badRequest.setErrorCode(ErrorCode.USER_EMAIL_EXISTS);
        return badRequest;
    }

    public static BadRequest userAlreadyExistByDocumentTypeAndDocumentNumber(String name, String documentNumber) {
        BadRequest badRequest = new BadRequest("El usuario con el tipo de documento " + name + " y número de documento " + documentNumber + " ya existe");
        badRequest.setErrorCode(ErrorCode.USER_DOCUMENT_EXISTS);
        return badRequest;
    }


}
