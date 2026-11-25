// ============================================================================================
// DOCUMENTACIÓN · incidents/dto/IncidentResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (IncidentResponse) sobre los
//                incidentes.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.incidents.dto;

import java.time.LocalDateTime;

public record IncidentResponse(Long id, Long missionId, String missionCode, String missionName, LocalDateTime date,
                               String type, String severity, String description, String reportedBy,
                               String status, String resolution) {
}
