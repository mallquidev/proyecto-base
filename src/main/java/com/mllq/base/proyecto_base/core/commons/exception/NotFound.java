package com.mllq.base.proyecto_base.core.commons.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotFound extends RuntimeException {
    private ErrorCode errorCode;

    public NotFound(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
