// ============================================================================================
// DOCUMENTACIÓN · reports/dto/MissionReport.java
//
// QUÉ ES:        Un paquete de datos (MissionReport) que viaja entre backend y
//                frontend para los reportes PDF.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.reports.dto;

import com.dronemission.evidence.dto.EvidenceResponse;
import com.dronemission.incidents.dto.IncidentResponse;
import com.dronemission.telemetry.dto.TelemetrySummary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/** Reporte completo de una misión (módulo 16). */
public record MissionReport(
        Long missionId,
        String code,
        String name,
        String type,
        String status,
        String priority,
        String objective,
        String location,
        double latitude,
        double longitude,
        LocalDate scheduledDate,
        LocalTime startTime,
        LocalTime estimatedEndTime,
        LocalDateTime actualStart,
        LocalDateTime actualEnd,
        Long durationMinutes,
        String observations,
        ClientInfo client,
        DroneInfo drone,
        PilotInfo pilot,
        String flightStrategy,
        int waypoints,
        TelemetrySummary telemetry,
        List<IncidentResponse> incidents,
        List<EvidenceResponse> evidences,
        String telemetryNotice
) {
    /**
     * Información de cliente.
     * @param name nombre (String)
     * @param type tipo (String)
     * @param document documento (String)
     * @param email correo (String)
     * @param phone teléfono (String)
     * @param city ciudad (String)
     * @return record
     */
    public record ClientInfo(String name, String type, String document, String email, String phone, String city) {
    }

    /**
     * Información de drone.
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param manufacturer fabricante (String)
     * @param model modelo (String)
     * @param type tipo (String)
     * @return record
     */
    public record DroneInfo(String code, String name, String manufacturer, String model, String type) {
    }

    /**
     * Información de piloto.
     * @param fullName nombre completo (String)
     * @param licenseNumber número de licencia de piloto (String)
     * @param licenseExpiresOn fecha de vencimiento de la licencia (LocalDate)
     * @return record
     */
    public record PilotInfo(String fullName, String licenseNumber, LocalDate licenseExpiresOn) {
    }
}
