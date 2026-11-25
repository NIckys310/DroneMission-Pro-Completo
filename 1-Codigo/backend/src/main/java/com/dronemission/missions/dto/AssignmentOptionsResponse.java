// ============================================================================================
// DOCUMENTACIÓN · missions/dto/AssignmentOptionsResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (AssignmentOptionsResponse) sobre
//                las misiones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.missions.dto;

import java.util.List;

/**
 * Opciones para asignar recursos a una misión. Cada drone y piloto indica si es
 * elegible y, si no lo es, el motivo (las mismas reglas que valida el backend al asignar).
 */
public record AssignmentOptionsResponse(int minimumBatteryPercent, List<DroneOption> drones, List<PilotOption> pilots) {

    /**
     * Opción de drone.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param type tipo (String)
     * @param status estado (String)
     * @param batteryLevel nivel de batería actual (0 a 100 %) (Integer)
     * @param eligible elegible (boolean)
     * @param reason motivo (String)
     * @return record
     */
    public record DroneOption(Long id, String code, String name, String type, String status, Integer batteryLevel,
                              boolean eligible, String reason) {
    }

    /**
     * Opción de piloto.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param fullName nombre completo (String)
     * @param status estado (String)
     * @param licenseExpiresOn fecha de vencimiento de la licencia (String)
     * @param eligible elegible (boolean)
     * @param reason motivo (String)
     * @return record
     */
    public record PilotOption(Long id, String fullName, String status, String licenseExpiresOn, boolean eligible,
                              String reason) {
    }
}
