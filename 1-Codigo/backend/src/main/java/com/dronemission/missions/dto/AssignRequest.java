// ============================================================================================
// DOCUMENTACIÓN · missions/dto/AssignRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (AssignRequest) para las misiones.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.missions.dto;

import jakarta.validation.constraints.NotNull;

public record AssignRequest(
        @NotNull(message = "Debes seleccionar un drone") Long droneId,
        @NotNull(message = "Debes seleccionar un piloto") Long pilotId
) {
}
