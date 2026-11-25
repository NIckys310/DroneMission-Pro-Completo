// ============================================================================================
// DOCUMENTACIÓN · missions/WaypointAction.java
//
// QUÉ ES:        Lo que hace el drone al llegar a un punto.
// QUÉ HACE:      DESPEGUE, PASO, FOTO, VIDEO, ESCANEO, FUMIGACION, ATERRIZAJE.
// SI LO CAMBIAS: Valor nuevo: agrégalo en el frontend (labels y los colores de
//                scene.ts).
// ============================================================================================
package com.dronemission.missions;

/** Acción que el drone virtual realiza al llegar a un punto del plan de vuelo. */
public enum WaypointAction {
    DESPEGUE, PASO, FOTO, VIDEO, ESCANEO, FUMIGACION, ATERRIZAJE
}
