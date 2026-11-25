// ============================================================================================
// DOCUMENTACIÓN · missions/dto/MissionResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (MissionResponse) sobre las
//                misiones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.missions.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Misión tal como la ve el frontend.
 * availableActions lo calcula el backend según el estado y el rol del usuario,
 * así el frontend solo muestra los botones permitidos sin duplicar reglas.
 */
public record MissionResponse(
        Long id,
        String code,
        String name,
        String description,
        String type,
        ClientRef client,
        String location,
        Double latitude,
        Double longitude,
        Integer targetAltitudeM,
        LocalDate scheduledDate,
        LocalTime startTime,
        LocalTime estimatedEndTime,
        String priority,
        String status,
        String objective,
        String observations,
        DroneRef drone,
        PilotRef pilot,
        String createdBy,
        LocalDateTime actualStart,
        LocalDateTime actualEnd,
        LocalDateTime createdAt,
        List<String> availableActions
) {
    /**
     * Ref de cliente.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param name nombre (String)
     * @return record
     */
    public record ClientRef(Long id, String name) {
    }

    /**
     * Ref de drone.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param type tipo (String)
     * @param status estado (String)
     * @param batteryLevel nivel de batería actual (0 a 100 %) (Integer)
     * @return record
     */
    public record DroneRef(Long id, String code, String name, String type, String status, Integer batteryLevel) {
    }

    /**
     * Ref de piloto.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param fullName nombre completo (String)
     * @param status estado (String)
     * @param licenseExpiresOn fecha de vencimiento de la licencia (LocalDate)
     * @return record
     */
    public record PilotRef(Long id, String fullName, String status, LocalDate licenseExpiresOn) {
    }
}
