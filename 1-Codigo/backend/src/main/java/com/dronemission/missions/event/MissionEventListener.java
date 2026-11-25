// ============================================================================================
// DOCUMENTACIÓN · missions/event/MissionEventListener.java
//
// QUÉ ES:        PATRÓN OBSERVER: el contrato de los "oyentes".
// QUÉ HACE:      Cualquier clase que lo implemente recibe los eventos de misiones
//                (hoy: auditoría y notificaciones).
// SI LO CAMBIAS: Reacción nueva (ej. enviar correo): crea una clase @Component que
//                implemente esta interfaz. Spring la conecta sola.
// ============================================================================================
package com.dronemission.missions.event;

/**
 * PATRÓN OBSERVER - Observador.
 * Cualquier componente interesado en los cambios de una misión implementa esta
 * interfaz. Hoy existen dos observadores: auditoría y notificaciones.
 */
public interface MissionEventListener {

    /**
     * Reacciona a evento de misión.
     * @param event evento (MissionEvent)
     */
    void onMissionEvent(MissionEvent event);
}
