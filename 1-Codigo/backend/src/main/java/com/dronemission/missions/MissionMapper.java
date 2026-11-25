// ============================================================================================
// DOCUMENTACIÓN · missions/MissionMapper.java
//
// QUÉ ES:        Traductor de Mission a lo que ve el frontend (MissionResponse).
// QUÉ HACE:      Junta los datos de la misión, el drone, el piloto y las acciones
//                disponibles.
// SI LO CAMBIAS: Si agregas un campo a la misión y no lo pones aquí, no llega a la
//                pantalla.
// ============================================================================================
package com.dronemission.missions;

import com.dronemission.missions.dto.MissionResponse;

import java.util.List;

/** Convierte una Mission en el DTO que consume el frontend. */
public final class MissionMapper {

    /**
     * Constructor de MissionMapper.
     */
    private MissionMapper() {
    }

    /**
     * Convierte a respuesta.
     * @param m (m) (Mission)
     * @param availableActions acciones disponibles (lista de String)
     * @return MissionResponse
     */
    public static MissionResponse toResponse(Mission m, List<String> availableActions) {
        var assignment = m.getActiveAssignment();
        MissionResponse.DroneRef drone = assignment.map(a -> new MissionResponse.DroneRef(a.getDrone().getId(),
                a.getDrone().getCode(), a.getDrone().getName(), a.getDrone().getType().name(),
                a.getDrone().getStatus().name(), a.getDrone().getBatteryLevel())).orElse(null);
        MissionResponse.PilotRef pilot = assignment.map(a -> new MissionResponse.PilotRef(a.getPilot().getId(),
                a.getPilot().getFullName(), a.getPilot().getStatus().name(), a.getPilot().getLicenseExpiresOn())).orElse(null);

        return new MissionResponse(m.getId(), m.getCode(), m.getName(), m.getDescription(), m.getType().name(),
                new MissionResponse.ClientRef(m.getClient().getId(), m.getClient().getName()),
                m.getLocation(), m.getLatitude(), m.getLongitude(), m.getTargetAltitudeM(), m.getScheduledDate(),
                m.getStartTime(), m.getEstimatedEndTime(), m.getPriority().name(), m.getStatus().name(),
                m.getObjective(), m.getObservations(), drone, pilot, m.getCreatedBy().getFullName(),
                m.getActualStart(), m.getActualEnd(), m.getCreatedAt(), availableActions);
    }
}
