// ============================================================================================
// DOCUMENTACIÓN · common/exception/DuplicateResourceException.java
//
// QUÉ ES:        Error de "ya existe" (HTTP 409).
// QUÉ HACE:      Se lanza si repites un valor único: código de drone, correo,
//                documento...
// SI LO CAMBIAS: El mensaje es lo que ve el usuario.
// ============================================================================================
package com.dronemission.common.exception;

/** Se lanza cuando se intenta registrar un valor que debe ser único (HTTP 409). */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructor de DuplicateResourceException.
     * @param message mensaje (String)
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}
