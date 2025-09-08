package com.mllq.base.proyect_base.core.commons.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Unauthorized extends RuntimeException {
    private ErrorCode errorCode;

    public Unauthorized(String message) {
        super(message);
    }

}
