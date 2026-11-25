// ============================================================================================
// DOCUMENTACIÓN · dashboard/dto/DashboardResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (DashboardResponse) sobre el
//                dashboard.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.dashboard.dto;

import com.dronemission.incidents.dto.IncidentResponse;
import com.dronemission.maintenance.dto.MaintenanceResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/** Datos del dashboard central (módulo 5.2). */
public record DashboardResponse(
        DroneStats drones,
        PilotStats pilots,
        Map<String, Long> missionsByStatus,
        List<CountItem> missionsByType,
        List<HoursItem> flightHoursByDrone,
        List<DayItem> missionsPerDay,
        List<MissionItem> activeMissions,
        List<MissionItem> upcomingMissions,
        List<IncidentResponse> recentIncidents,
        List<MaintenanceResponse> pendingMaintenance,
        List<Alert> alerts,
        long openIncidents
) {
    /**
     * Estadísticas de drone.
     * @param total total (long)
     * @param available disponibles (long)
     * @param inMission en misión (long)
     * @param inMaintenance en mantenimiento (long)
     * @param outOfService out de servicio (long)
     * @param totalFlightHours horas de vuelo total (BigDecimal)
     * @return record
     */
    public record DroneStats(long total, long available, long inMission, long inMaintenance, long outOfService,
                             BigDecimal totalFlightHours) {
    }

    /**
     * Estadísticas de piloto.
     * @param total total (long)
     * @param available disponibles (long)
     * @param inMission en misión (long)
     * @param expiredLicenses licenses de expired (long)
     * @return record
     */
    public record PilotStats(long total, long available, long inMission, long expiredLicenses) {
    }

    /**
     * Elemento de cantidad.
     * @param label etiqueta (String)
     * @param count cantidad (long)
     * @return record
     */
    public record CountItem(String label, long count) {
    }

    /**
     * Elemento de horas.
     * @param drone drone (String)
     * @param hours horas (BigDecimal)
     * @return record
     */
    public record HoursItem(String drone, BigDecimal hours) {
    }

    /**
     * Elemento de día.
     * @param date fecha (LocalDate)
     * @param scheduled programada (long)
     * @param finished finished (long)
     * @return record
     */
    public record DayItem(LocalDate date, long scheduled, long finished) {
    }

    /**
     * Elemento de misión.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param status estado (String)
     * @param type tipo (String)
     * @param date fecha (LocalDate)
     * @param startTime hora de inicio (LocalTime)
     * @param clientName nombre de cliente (String)
     * @param droneCode código de drone (String)
     * @param pilotName nombre de piloto (String)
     * @return record
     */
    public record MissionItem(Long id, String code, String name, String status, String type, LocalDate date,
                              LocalTime startTime, String clientName, String droneCode, String pilotName) {
    }

    /** level: info | warning | error */
    public record Alert(String level, String message, String link) {
    }
}
