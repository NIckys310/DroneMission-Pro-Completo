// ============================================================================================
// DOCUMENTACIÓN · pilots/dto/PilotRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (PilotRequest) para los pilotos.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.pilots.dto;

import com.dronemission.pilots.PilotStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PilotRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String firstName,
        @NotBlank(message = "Los apellidos son obligatorios") @Size(max = 80) String lastName,
        @NotBlank(message = "El documento es obligatorio") @Size(max = 30) String document,
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo no es válido") String email,
        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9+()\\s-]{7,30}$", message = "El teléfono no es válido") String phone,
        @NotBlank(message = "El número de licencia es obligatorio") @Size(max = 40) String licenseNumber,
        @NotNull(message = "La fecha de vencimiento de la licencia es obligatoria") LocalDate licenseExpiresOn,
        @PositiveOrZero(message = "Las horas de vuelo no pueden ser negativas") BigDecimal flightHours,
        PilotStatus status,
        List<@Valid CertificationDto> certifications
) {
}
