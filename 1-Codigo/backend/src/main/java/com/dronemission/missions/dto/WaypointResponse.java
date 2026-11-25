// ============================================================================================
// DOCUMENTACIÓN · missions/dto/WaypointResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (WaypointResponse) sobre las
//                misiones.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.missions.dto;

public record WaypointResponse(int sequence, double latitude, double longitude, int altitudeM, String action) {
}
