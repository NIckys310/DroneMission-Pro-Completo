// ============================================================================================
// DOCUMENTACIÓN · incidents/dto/IncidentStatusRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (IncidentStatusRequest) para los
//                incidentes.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.incidents.dto;

import com.dronemission.incidents.IncidentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidentStatusRequest(
        @NotNull(message = "El estado es obligatorio") IncidentStatus status,
        @Size(max = 1000) String resolution
) {
}
