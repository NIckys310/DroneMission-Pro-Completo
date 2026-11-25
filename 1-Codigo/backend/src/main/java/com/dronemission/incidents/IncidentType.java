// ============================================================================================
// DOCUMENTACIÓN · incidents/IncidentType.java
//
// QUÉ ES:        Tipos de incidente.
// QUÉ HACE:      BATERIA_BAJA, PERDIDA_SENAL, OBSTACULO, ERROR_TECNICO, CLIMA,
//                ATERRIZAJE_EMERGENCIA, OTRO.
// SI LO CAMBIAS: Valor nuevo: agrégalo en el frontend (types y labels).
// ============================================================================================
package com.dronemission.incidents;

public enum IncidentType {
    BATERIA_BAJA, PERDIDA_SENAL, OBSTACULO, ERROR_TECNICO, CLIMA, ATERRIZAJE_EMERGENCIA, OTRO
}
