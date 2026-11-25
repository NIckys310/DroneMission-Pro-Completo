// ============================================================================================
// DOCUMENTACIÓN · audit/AuditMissionListener.java
//
// QUÉ ES:        Un "oyente" (patrón Observer) de las misiones.
// QUÉ HACE:      Cada vez que una misión cambia (se crea, se asigna, despega, se
//                cancela...) anota el evento en la bitácora.
// SI LO CAMBIAS: Si lo borras, las misiones siguen funcionando pero ya no quedan en la
//                auditoría. Nada más se rompe.
// ============================================================================================
package com.dronemission.audit;

import com.dronemission.missions.event.MissionEvent;
import com.dronemission.missions.event.MissionEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** Observador que deja en la bitácora de auditoría cada evento de una misión. */
@Component
@RequiredArgsConstructor
public class AuditMissionListener implements MissionEventListener {

    /** Dependencia inyectada por Spring (por constructor): servicio de auditoría. */
    private final AuditService auditService;

    /**
     * Reacciona a evento de misión.
     * Implementa un método de la interfaz o clase padre.
     * @param event evento (MissionEvent)
     */
    @Override
    public void onMissionEvent(MissionEvent event) {
        String action = switch (event.type()) {
            case CREADA -> "CREAR";
            case ACTUALIZADA -> "ACTUALIZAR";
            case ENVIADA -> "ENVIAR";
            case ASIGNADA -> "ASIGNAR";
            case PREPARADA -> "PREPARAR";
            case INICIADA -> "INICIAR";
            case PAUSADA -> "PAUSAR";
            case REANUDADA -> "REANUDAR";
            case FINALIZADA -> "FINALIZAR";
            case CANCELADA -> "CANCELAR";
            case ELIMINADA -> "ELIMINAR";
            case INCIDENTE_CRITICO -> "INCIDENTE_CRITICO";
        };
        String description = "Misión " + event.mission().getCode()
                + (event.detail() != null && !event.detail().isBlank() ? ": " + event.detail() : "");
        auditService.log(action, "MISION", event.mission().getId(), description);
    }
}
