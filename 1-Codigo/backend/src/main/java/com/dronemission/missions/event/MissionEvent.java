// ============================================================================================
// DOCUMENTACIÓN · missions/event/MissionEvent.java
//
// QUÉ ES:        PATRÓN OBSERVER: el "mensaje" que se envía cuando una misión cambia.
// QUÉ HACE:      Lleva el tipo de evento, la misión, quién lo hizo y un detalle.
// SI LO CAMBIAS: Si agregas un dato, todos los oyentes lo reciben automáticamente.
// ============================================================================================
package com.dronemission.missions.event;

import com.dronemission.missions.Mission;

/** Mensaje que el publicador envía a todos los observadores. */
public record MissionEvent(MissionEventType type, Mission mission, String detail) {
}
