// ============================================================================================
// DOCUMENTACIÓN · common/exception/ResourceNotFoundException.java
//
// QUÉ ES:        Error de "no existe" (HTTP 404).
// QUÉ HACE:      Se lanza cuando buscas un id que no está en la base.
// SI LO CAMBIAS: Úsala en cualquier findById que no encuentre nada.
// ============================================================================================
package com.dronemission.common.exception;

/** Se lanza cuando un recurso solicitado no existe (HTTP 404). */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor de ResourceNotFoundException.
     * @param resource recurso (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     */
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " con id " + id + " no existe");
    }

    /**
     * Constructor de ResourceNotFoundException.
     * @param message mensaje (String)
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
