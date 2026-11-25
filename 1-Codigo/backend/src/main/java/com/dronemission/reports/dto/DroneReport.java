// ============================================================================================
// DOCUMENTACIÓN · reports/dto/DroneReport.java
//
// QUÉ ES:        Un paquete de datos (DroneReport) que viaja entre backend y frontend
//                para los reportes PDF.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DroneReport(long total, long available, long inMission, long inMaintenance, long outOfService,
                          BigDecimal totalFlightHours, List<Row> drones) {

    /**
     * Fila.
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param name nombre (String)
     * @param model modelo (String)
     * @param type tipo (String)
     * @param status estado (String)
     * @param batteryLevel nivel de batería actual (0 a 100 %) (int)
     * @param flightHours horas de vuelo acumuladas (BigDecimal)
     * @param nextMaintenanceOn fecha del próximo mantenimiento (LocalDate)
     * @return record
     */
    public record Row(String code, String name, String model, String type, String status, int batteryLevel,
                      BigDecimal flightHours, LocalDate nextMaintenanceOn) {
    }
}
