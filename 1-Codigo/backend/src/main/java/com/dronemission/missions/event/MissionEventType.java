// ============================================================================================
// DOCUMENTACIÓN · missions/event/MissionEventType.java
//
// QUÉ ES:        La lista de eventos de una misión.
// QUÉ HACE:      CREADA, ENVIADA, ASIGNADA, PREPARADA, INICIADA, PAUSADA, FINALIZADA,
//                CANCELADA, INCIDENTE_CRITICO...
// SI LO CAMBIAS: Evento nuevo: valor aquí, publish() en el servicio y (si quieres
//                aviso) un case en NotificationMissionListener.
// ============================================================================================
package com.dronemission.missions.event;

/** Eventos relevantes en la vida de una misión (cada uno se audita y puede notificar). */
public enum MissionEventType {
    CREADA, ACTUALIZADA, ENVIADA, ASIGNADA, PREPARADA, INICIADA, PAUSADA, REANUDADA,
    FINALIZADA, CANCELADA, ELIMINADA, INCIDENTE_CRITICO
}
