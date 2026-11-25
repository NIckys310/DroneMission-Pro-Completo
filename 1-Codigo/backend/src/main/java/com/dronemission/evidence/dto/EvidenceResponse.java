// ============================================================================================
// DOCUMENTACIÓN · evidence/dto/EvidenceResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (EvidenceResponse) sobre las
//                evidencias de las misiones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.evidence.dto;

import java.time.LocalDateTime;

public record EvidenceResponse(Long id, Long missionId, String type, String name, String url, boolean storedFile,
                               String description, boolean visibleToClient, String uploadedBy, LocalDateTime date) {
}
