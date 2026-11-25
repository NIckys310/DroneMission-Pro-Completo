// ============================================================================================
// DOCUMENTACIÓN · reports/dto/PilotReport.java
//
// QUÉ ES:        Un paquete de datos (PilotReport) que viaja entre backend y frontend
//                para los reportes PDF.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.reports.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PilotReport(long total, long available, long expiredLicenses, BigDecimal totalFlightHours, List<Row> pilots) {

    /**
     * Fila.
     * @param fullName nombre completo (String)
     * @param licenseNumber número de licencia de piloto (String)
     * @param licenseExpiresOn fecha de vencimiento de la licencia (LocalDate)
     * @param licenseValid licencia valid (boolean)
     * @param status estado (String)
     * @param flightHours horas de vuelo acumuladas (BigDecimal)
     * @param missions misiones (long)
     * @param finishedMissions misiones de finished (long)
     * @return record
     */
    public record Row(String fullName, String licenseNumber, LocalDate licenseExpiresOn, boolean licenseValid,
                      String status, BigDecimal flightHours, long missions, long finishedMissions) {
    }
}
