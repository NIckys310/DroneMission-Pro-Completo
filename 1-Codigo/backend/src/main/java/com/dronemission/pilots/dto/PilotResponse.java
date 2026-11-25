// ============================================================================================
// DOCUMENTACIÓN · pilots/dto/PilotResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (PilotResponse) sobre los pilotos.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.pilots.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PilotResponse(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String document,
        String email,
        String phone,
        String licenseNumber,
        LocalDate licenseExpiresOn,
        boolean licenseValid,
        BigDecimal flightHours,
        String status,
        boolean active,
        String userEmail,
        List<CertificationDto> certifications,
        long missionCount
) {
}
