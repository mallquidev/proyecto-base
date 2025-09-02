package com.mllq.base.proyect_base.core.commons.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtException extends RuntimeException {
    private ErrorCode errorCode;

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
