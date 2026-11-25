// ============================================================================================
// DOCUMENTACIÓN · notifications/dto/NotificationResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (NotificationResponse) sobre las
//                notificaciones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.notifications.dto;

import java.time.LocalDateTime;

public record NotificationResponse(Long id, String title, String message, Long missionId, boolean read,
                                   LocalDateTime date) {
}
