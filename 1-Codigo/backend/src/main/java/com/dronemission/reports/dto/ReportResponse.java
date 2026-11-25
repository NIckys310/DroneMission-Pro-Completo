// ============================================================================================
// DOCUMENTACIÓN · reports/dto/ReportResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (ReportResponse) sobre los reportes
//                PDF.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.reports.dto;

import java.time.LocalDateTime;

/** Registro del historial de reportes generados. */
public record ReportResponse(Long id, String type, String title, Long missionId, String missionCode,
                             String generatedBy, LocalDateTime generatedAt, String summary) {
}
