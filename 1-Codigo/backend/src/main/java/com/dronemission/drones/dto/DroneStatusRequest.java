// ============================================================================================
// DOCUMENTACIÓN · drones/dto/DroneStatusRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (DroneStatusRequest) para los drones.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.drones.dto;

import com.dronemission.drones.DroneStatus;
import jakarta.validation.constraints.NotNull;

public record DroneStatusRequest(
        @NotNull(message = "El estado es obligatorio") DroneStatus status,
        String reason
) {
}
