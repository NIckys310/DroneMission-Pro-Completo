// ============================================================================================
// DOCUMENTACIÓN · evidence/dto/EvidenceRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (EvidenceRequest) para las evidencias
//                de las misiones.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.evidence.dto;

import com.dronemission.evidence.EvidenceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Evidencia registrada con una URL (sin subir archivo). */
public record EvidenceRequest(
        @NotNull(message = "El tipo de evidencia es obligatorio") EvidenceType type,
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 150) String name,
        @Size(max = 500) String url,
        @Size(max = 1000) String description,
        Boolean visibleToClient
) {
}
