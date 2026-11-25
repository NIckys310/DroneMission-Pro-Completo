// ============================================================================================
// DOCUMENTACIÓN · telemetry/dto/TelemetryPoint.java
//
// QUÉ ES:        Un paquete de datos (TelemetryPoint) que viaja entre backend y
//                frontend para la telemetría (datos del vuelo).
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.telemetry.dto;

import java.time.LocalDateTime;

public record TelemetryPoint(Long id, LocalDateTime recordedAt, double latitude, double longitude, double altitudeM,
                             double speedKmh, int battery, double distanceM, double temperatureC, int flightTimeS,
                             int currentWaypoint, boolean simulated) {
}
