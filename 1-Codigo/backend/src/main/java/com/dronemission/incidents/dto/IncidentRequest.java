// ============================================================================================
// DOCUMENTACIÓN · incidents/dto/IncidentRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (IncidentRequest) para los
//                incidentes.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.incidents.dto;

import com.dronemission.incidents.IncidentSeverity;
import com.dronemission.incidents.IncidentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidentRequest(
        @NotNull(message = "El tipo de incidente es obligatorio") IncidentType type,
        @NotNull(message = "La severidad es obligatoria") IncidentSeverity severity,
        @NotBlank(message = "La descripción es obligatoria") @Size(max = 1000) String description
) {
}
