package com.mllq.base.proyect_base.core.commons.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Conflict extends RuntimeException {
    private ErrorCode errorCode;

    public Conflict(String message) {
        super(message);
    }
}
