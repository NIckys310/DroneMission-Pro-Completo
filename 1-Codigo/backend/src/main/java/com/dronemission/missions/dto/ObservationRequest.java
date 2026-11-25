// ============================================================================================
// DOCUMENTACIÓN · missions/dto/ObservationRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (ObservationRequest) para las
//                misiones.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.missions.dto;

import jakarta.validation.constraints.Size;

/** Observación opcional (al finalizar) u obligatoria (al registrar observaciones). */
public record ObservationRequest(@Size(max = 500) String text) {
}
