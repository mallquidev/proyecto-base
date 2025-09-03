package com.mllq.base.proyecto_base.core.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCode {
    public static final ErrorCode ENDPOINT_NOT_FOUND = ErrorCode.of(
            "api-01","endpoint no encontrado"
    );
    public static final ErrorCode VALIDATION_FAILED = ErrorCode.of(
            "valid-001", "Validaciones fallida"
    );
    public static final ErrorCode USER_EMAIL_EXISTS = ErrorCode.of(
            "usr-002","El email ya existe en el sistema"
    );
    public static final ErrorCode USER_DOCUMENT_EXISTS = ErrorCode.of(
            "usr-003", "Ya existe un usuario con el mismo numero de documento y tipo"
    );

    public static ErrorCode TOKEN_INVALID = ErrorCode.of(
            "auth-001", "El token Jwt Es Invalido"
    );
    public static final ErrorCode USER_NOT_SESSION = ErrorCode.of(
            "auth-002", "El usuario no tiene una sesión activa"
    );
    public static final ErrorCode EXPIRATION_EXPIRED =  ErrorCode.of(
            "jwt-001", "EL token a expirado"
    );
    public static final ErrorCode CREDENTIALS_INVALID = ErrorCode.of(
            "auth-004", "Credenciales invalidas"
    );
    public static final ErrorCode USER_NOT_LOGIN_FOR_DELETED = ErrorCode.of(
            "auth-005", "El usuario ha sido eliminado, no puede iniciar sesión"
    );
    public static final ErrorCode NOT_ACCESS_SYSTEM =   ErrorCode.of(
            "bad-001", "No eres un usuario valido para el sistema"
    );
    public static final ErrorCode USER_NOT_FOUND = ErrorCode.of(
            "usr-001", "El usuario no existe en la base de datos"
    );
    private String code;
    private String message;
    public static ErrorCode of(String code, String message) {
        return ErrorCode.builder()
                .code(code)
                .message(message)
                .build();
    }



}