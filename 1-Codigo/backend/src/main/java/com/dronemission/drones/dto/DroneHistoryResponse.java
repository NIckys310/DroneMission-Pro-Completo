// ============================================================================================
// DOCUMENTACIÓN · drones/dto/DroneHistoryResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (DroneHistoryResponse) sobre los
//                drones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.drones.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Historial de un drone: misiones en las que participó y mantenimientos. */
public record DroneHistoryResponse(List<MissionEntry> missions, List<MaintenanceEntry> maintenance) {

    /**
     * Entry de misión.
     * @param missionId identificador de misión (Long)
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param status estado (String)
     * @param scheduledDate fecha programada (LocalDate)
     * @param pilotName nombre de piloto (String)
     * @param assignedAt fecha y hora de la asignación (LocalDateTime)
     * @return record
     */
    public record MissionEntry(Long missionId, String code, String name, String status, LocalDate scheduledDate,
                               String pilotName, LocalDateTime assignedAt) {
    }

    /**
     * Entry de mantenimiento.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param type tipo (String)
     * @param status estado (String)
     * @param date fecha (LocalDate)
     * @param description descripción (String)
     * @param cost costo (java.math.BigDecimal)
     * @param technicianName nombre de técnico (String)
     * @return record
     */
    public record MaintenanceEntry(Long id, String type, String status, LocalDate date, String description,
                                   java.math.BigDecimal cost, String technicianName) {
    }
}
