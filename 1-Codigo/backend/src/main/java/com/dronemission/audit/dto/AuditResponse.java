// ============================================================================================
// DOCUMENTACIÓN · audit/dto/AuditResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (AuditResponse) sobre la auditoría
//                (bitácora de quién hizo qué).
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.audit.dto;

import java.time.LocalDateTime;

public record AuditResponse(Long id, String userEmail, String action, String entity, Long entityId,
                            String description, LocalDateTime date) {
}
