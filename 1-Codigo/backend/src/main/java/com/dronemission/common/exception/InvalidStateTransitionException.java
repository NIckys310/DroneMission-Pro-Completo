// ============================================================================================
// DOCUMENTACIÓN · common/exception/InvalidStateTransitionException.java
//
// QUÉ ES:        Error de "ese cambio de estado no se puede" (HTTP 409).
// QUÉ HACE:      Lo lanza Mission.changeStatus() cuando MissionStatus no permite el
//                paso.
// SI LO CAMBIAS: No la quites: protege la máquina de estados.
// ============================================================================================
package com.dronemission.common.exception;

/** Se lanza cuando se intenta un cambio de estado no permitido (HTTP 409). */
public class InvalidStateTransitionException extends RuntimeException {

    /**
     * Constructor de InvalidStateTransitionException.
     * @param message mensaje (String)
     */
    public InvalidStateTransitionException(String message) {
        super(message);
    }
}
