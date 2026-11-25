// ============================================================================================
// DOCUMENTACIÓN · notifications/NotificationController.java
//
// QUÉ ES:        La puerta HTTP de notificaciones.
// QUÉ HACE:      Listar mis notificaciones y marcarlas como leídas.
// SI LO CAMBIAS: Si cambias la ruta, cambia notificationApi en el frontend.
// ============================================================================================
package com.dronemission.notifications;

import com.dronemission.notifications.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notificaciones")
public class NotificationController {

    /** Dependencia inyectada por Spring (por constructor): servicio de notificación. */
    private final NotificationService notificationService;

    /**
     * Mío.
     * Endpoint GET /api/notifications (lo llama el frontend desde api/endpoints.ts).
     * @return lista de NotificationResponse
     */
    @GetMapping
    @Operation(summary = "Mis últimas notificaciones")
    public List<NotificationResponse> mine() {
        return notificationService.findMine();
    }

    /**
     * Cantidad no leídas.
     * Endpoint GET /api/notifications/unread-count (lo llama el frontend desde api/endpoints.ts).
     * @return Map<String, Long>
     */
    @GetMapping("/unread-count")
    public Map<String, Long> unreadCount() {
        return Map.of("count", notificationService.countUnread());
    }

    /**
     * Marca leída de como.
     * Endpoint PATCH /api/notifications/{id}/read (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Marca leída de como de todos.
     * Endpoint POST /api/notifications/read-all (lo llama el frontend desde api/endpoints.ts).
     * @return ResponseEntity<Void>
     */
    @PostMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead() {
        notificationService.markAllAsRead();
        return ResponseEntity.noContent().build();
    }
}
