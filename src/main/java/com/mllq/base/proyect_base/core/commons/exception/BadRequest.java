package com.mllq.base.proyect_base.core.commons.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BadRequest extends RuntimeException {
    private ErrorCode errorCode;
    public BadRequest(String message) {
        super(message);
    }
    public BadRequest(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
