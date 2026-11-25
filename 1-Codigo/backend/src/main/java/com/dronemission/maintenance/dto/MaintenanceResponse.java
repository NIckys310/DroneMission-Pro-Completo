// ============================================================================================
// DOCUMENTACIÓN · maintenance/dto/MaintenanceResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (MaintenanceResponse) sobre los
//                mantenimientos.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.maintenance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MaintenanceResponse(Long id, Long droneId, String droneCode, String droneName, String droneStatus,
                                  Long technicianId, String technicianName, String type, LocalDate date,
                                  String description, BigDecimal cost, String status, LocalDate nextDate,
                                  String observations, LocalDateTime startedAt, LocalDateTime completedAt) {
}
