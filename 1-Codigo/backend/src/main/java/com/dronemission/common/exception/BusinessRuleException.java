// ============================================================================================
// DOCUMENTACIÓN · common/exception/BusinessRuleException.java
//
// QUÉ ES:        Error de "regla de negocio rota" (HTTP 400).
// QUÉ HACE:      Se lanza cuando algo no está permitido, por ejemplo asignar un drone
//                en mantenimiento. El mensaje se muestra tal cual en pantalla.
// SI LO CAMBIAS: Para una regla nueva basta con: throw new
//                BusinessRuleException("mensaje claro").
// ============================================================================================
package com.dronemission.common.exception;

/**
 * Se lanza cuando una operación viola una regla de negocio (HTTP 400).
 * Ejemplo: asignar un drone que está en mantenimiento.
 */
public class BusinessRuleException extends RuntimeException {

    /**
     * Constructor de BusinessRuleException.
     * @param message mensaje (String)
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
