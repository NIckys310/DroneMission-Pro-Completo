// ============================================================================================
// DOCUMENTACIÓN · telemetry/dto/TelemetryFeed.java
//
// QUÉ ES:        Un paquete de datos (TelemetryFeed) que viaja entre backend y
//                frontend para la telemetría (datos del vuelo).
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.telemetry.dto;

import java.util.List;

/** Respuesta del endpoint de telemetría que consulta periódicamente el frontend. */
public record TelemetryFeed(
        boolean simulated,
        String notice,
        String missionStatus,
        boolean live,
        boolean routeCompleted,
        int totalWaypoints,
        TelemetrySummary summary,
        List<TelemetryPoint> points
) {
}
