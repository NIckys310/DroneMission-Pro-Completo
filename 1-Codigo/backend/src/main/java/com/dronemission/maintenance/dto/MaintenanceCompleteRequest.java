// ============================================================================================
// DOCUMENTACIÓN · maintenance/dto/MaintenanceCompleteRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (MaintenanceCompleteRequest) para los
//                mantenimientos.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.maintenance.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Datos opcionales al finalizar un mantenimiento (costo final, próxima fecha, observaciones). */
public record MaintenanceCompleteRequest(
        @PositiveOrZero(message = "El costo no puede ser negativo") BigDecimal finalCost,
        LocalDate nextDate,
        @Size(max = 1000) String observations
) {
}
