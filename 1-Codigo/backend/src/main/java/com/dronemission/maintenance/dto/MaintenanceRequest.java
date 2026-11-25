// ============================================================================================
// DOCUMENTACIÓN · maintenance/dto/MaintenanceRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (MaintenanceRequest) para los
//                mantenimientos.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.maintenance.dto;

import com.dronemission.maintenance.MaintenanceType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MaintenanceRequest(
        @NotNull(message = "El drone es obligatorio") Long droneId,
        Long technicianId,
        @NotNull(message = "El tipo de mantenimiento es obligatorio") MaintenanceType type,
        @NotNull(message = "La fecha es obligatoria") LocalDate date,
        @NotBlank(message = "La descripción es obligatoria") @Size(max = 1000) String description,
        @NotNull(message = "El costo es obligatorio") @PositiveOrZero(message = "El costo no puede ser negativo") BigDecimal cost,
        LocalDate nextDate,
        @Size(max = 1000) String observations,
        Boolean startNow
) {
}
