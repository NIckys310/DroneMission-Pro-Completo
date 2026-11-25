// ============================================================================================
// DOCUMENTACIÓN · pilots/dto/PilotMissionEntry.java
//
// QUÉ ES:        Un paquete de datos (PilotMissionEntry) que viaja entre backend y
//                frontend para los pilotos.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.pilots.dto;

import java.time.LocalDate;

/** Misión en el historial de un piloto. */
public record PilotMissionEntry(Long missionId, String code, String name, String status, LocalDate scheduledDate,
                                String droneCode) {
}
