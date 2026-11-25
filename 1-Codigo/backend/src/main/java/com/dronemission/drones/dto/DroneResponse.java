// ============================================================================================
// DOCUMENTACIÓN · drones/dto/DroneResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (DroneResponse) sobre los drones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.drones.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DroneResponse(
        Long id,
        String code,
        String name,
        String manufacturer,
        String model,
        String serialNumber,
        String type,
        String status,
        Integer batteryCapacityMah,
        Integer batteryLevel,
        BigDecimal flightHours,
        Integer maxSpeedKmh,
        Integer autonomyMinutes,
        LocalDate acquiredOn,
        LocalDate lastMaintenanceOn,
        LocalDate nextMaintenanceOn,
        boolean maintenanceDue,
        String description,
        boolean active
) {
}
