// ============================================================================================
// DOCUMENTACIÓN · missions/event/MissionEventPublisher.java
//
// QUÉ ES:        PATRÓN OBSERVER: el "altavoz".
// QUÉ HACE:      Los servicios llaman a publish() y él reparte el evento a todos los
//                oyentes, sin que el servicio sepa quiénes son.
// SI LO CAMBIAS: No hace falta tocarlo para agregar o quitar oyentes.
// ============================================================================================
package com.dronemission.missions.event;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PATRÓN OBSERVER - Sujeto (publicador).
 * Los servicios de misiones solo llaman a publish(); no saben quién escucha.
 * Spring inyecta automáticamente todos los MissionEventListener registrados,
 * así que agregar un nuevo observador (por ejemplo, enviar correos) no exige
 * modificar MissionService (principio abierto/cerrado).
 */
@Component
public class MissionEventPublisher {

    /** Dependencia inyectada por Spring (por constructor): lista. */
    private final List<MissionEventListener> listeners;

    /**
     * Constructor de MissionEventPublisher.
     * @param listeners observadores (lista de MissionEventListener)
     */
    public MissionEventPublisher(List<MissionEventListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * Publica evento de misión.
     * @param event evento (MissionEvent)
     */
    public void publish(MissionEvent event) {
        listeners.forEach(listener -> listener.onMissionEvent(event));
    }
}
