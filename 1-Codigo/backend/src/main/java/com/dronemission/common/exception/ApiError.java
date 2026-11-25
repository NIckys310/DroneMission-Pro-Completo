// ============================================================================================
// DOCUMENTACIÓN · common/exception/ApiError.java
//
// QUÉ ES:        El formato único de los errores de la API.
// QUÉ HACE:      Todo error sale como JSON con fecha, código, mensaje y, si aplica,
//                errores por campo.
// SI LO CAMBIAS: Si cambias un nombre de campo, cambia la interface ApiError del
//                frontend (types/index.ts) y errorMessage() en api/client.ts.
// ============================================================================================
package com.dronemission.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato único de error que devuelve la API.
 * Ejemplo: {"timestamp": "...", "status": 400, "error": "Bad Request",
 *           "message": "El drone seleccionado no está disponible", "path": "/api/missions/15/assign"}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    /**
     * De.
     * @param status estado (int)
     * @param error error (String)
     * @param message mensaje (String)
     * @param path ruta (String)
     * @return ApiError
     */
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(LocalDateTime.now(), status, error, message, path, null);
    }
}
