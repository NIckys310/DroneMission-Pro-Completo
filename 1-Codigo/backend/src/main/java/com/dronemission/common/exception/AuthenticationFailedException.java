// ============================================================================================
// DOCUMENTACIÓN · common/exception/AuthenticationFailedException.java
//
// QUÉ ES:        Error de "usuario o contraseña incorrectos" (HTTP 401).
// QUÉ HACE:      Se lanza en el login; GlobalExceptionHandler lo convierte en 401.
// SI LO CAMBIAS: Úsala solo para fallos de autenticación.
// ============================================================================================
package com.dronemission.common.exception;

/** Credenciales inválidas o usuario inactivo (HTTP 401). */
public class AuthenticationFailedException extends RuntimeException {

    /**
     * Constructor de AuthenticationFailedException.
     * @param message mensaje (String)
     */
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
