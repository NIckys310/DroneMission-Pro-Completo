// ============================================================================================
// DOCUMENTACIÓN · missions/dto/FlightPlanResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (FlightPlanResponse) sobre las
//                misiones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.missions.dto;

import java.util.List;

/** Plan de vuelo programado que se "carga" en el drone virtual antes del despegue. */
public record FlightPlanResponse(
        String strategy,
        int minimumBatteryPercent,
        List<String> compatibleDroneTypes,
        double cruiseSpeedKmh,
        double estimatedDistanceM,
        double estimatedDurationMin,
        double estimatedBatteryUsePercent,
        List<WaypointResponse> waypoints
) {
}
