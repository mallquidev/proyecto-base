package com.mllq.base.proyect_base.core.commons.exception;

public class CommonsException {
    public static BadRequest expirationExpired() {
        BadRequest badRequest = new BadRequest("El tiempo de expiración ha expirado");
        badRequest.setErrorCode(ErrorCode.EXPIRATION_EXPIRED);
        return badRequest;
    }
}
