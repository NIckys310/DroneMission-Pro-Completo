// ============================================================================================
// DOCUMENTACIÓN · missions/strategy/PlannedWaypoint.java
//
// QUÉ ES:        Un punto calculado por una estrategia, antes de guardarse.
// QUÉ HACE:      Lo genera la estrategia y FlightPlanService lo convierte en Waypoint.
// SI LO CAMBIAS: Uso interno.
// ============================================================================================
package com.dronemission.missions.strategy;

import com.dronemission.missions.WaypointAction;

/** Punto calculado por una estrategia antes de guardarse como Waypoint. */
public record PlannedWaypoint(GeoPoint point, int altitudeM, WaypointAction action) {
}
