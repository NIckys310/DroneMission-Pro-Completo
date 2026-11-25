// ============================================================================================
// DOCUMENTACIÓN · telemetry/dto/TelemetrySummary.java
//
// QUÉ ES:        Un paquete de datos (TelemetrySummary) que viaja entre backend y
//                frontend para la telemetría (datos del vuelo).
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.telemetry.dto;

/** Resumen de la telemetría de una misión (para el detalle y los reportes). */
public record TelemetrySummary(int readings, double maxAltitudeM, double avgSpeedKmh, double maxSpeedKmh,
                               double distanceM, int flightTimeS, int startBattery, int endBattery,
                               int batteryUsed, double maxTemperatureC) {

    /**
     * Vacío.
     * @return TelemetrySummary
     */
    public static TelemetrySummary empty() {
        return new TelemetrySummary(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }
}
