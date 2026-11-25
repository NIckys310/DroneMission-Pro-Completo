// ============================================================================================
// DOCUMENTACIÓN · reports/dto/MaintenanceReport.java
//
// QUÉ ES:        Un paquete de datos (MaintenanceReport) que viaja entre backend y
//                frontend para los reportes PDF.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record MaintenanceReport(long total, long scheduled, long inProgress, long completed, long cancelled,
                                BigDecimal totalCost, List<CostByDrone> costByDrone, List<Row> history) {

    /**
     * Costo por drone.
     * @param droneCode código de drone (String)
     * @param droneName nombre de drone (String)
     * @param count cantidad (long)
     * @param cost costo (BigDecimal)
     * @return record
     */
    public record CostByDrone(String droneCode, String droneName, long count, BigDecimal cost) {
    }

    /**
     * Fila.
     * @param date fecha (LocalDate)
     * @param droneCode código de drone (String)
     * @param type tipo (String)
     * @param status estado (String)
     * @param technician técnico (String)
     * @param cost costo (BigDecimal)
     * @param description descripción (String)
     * @return record
     */
    public record Row(LocalDate date, String droneCode, String type, String status, String technician,
                      BigDecimal cost, String description) {
    }
}
