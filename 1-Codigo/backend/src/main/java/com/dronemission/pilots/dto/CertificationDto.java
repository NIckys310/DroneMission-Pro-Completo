// ============================================================================================
// DOCUMENTACIÓN · pilots/dto/CertificationDto.java
//
// QUÉ ES:        Un paquete de datos (CertificationDto) que viaja entre backend y
//                frontend para los pilotos.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.pilots.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CertificationDto(
        Long id,
        @NotBlank(message = "El nombre de la certificación es obligatorio") String name,
        @NotBlank(message = "La entidad emisora es obligatoria") String issuer,
        @NotNull(message = "La fecha de emisión es obligatoria") LocalDate issuedOn,
        LocalDate expiresOn
) {
}
