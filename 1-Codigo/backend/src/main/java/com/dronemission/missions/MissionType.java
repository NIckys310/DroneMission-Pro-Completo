// ============================================================================================
// DOCUMENTACIÓN · missions/MissionType.java
//
// QUÉ ES:        Los tipos de misión.
// QUÉ HACE:      INSPECCION, AGRICULTURA, VIGILANCIA, TOPOGRAFIA, FOTOGRAFIA. Cada uno
//                tiene su estrategia de vuelo.
// SI LO CAMBIAS: Tipo nuevo: valor aquí + una clase Strategy en missions/strategy/ +
//                agregarla en MissionStrategyTest + frontend (types y labels).
// ============================================================================================
package com.dronemission.missions;

public enum MissionType {
    INSPECCION, AGRICULTURA, VIGILANCIA, TOPOGRAFIA, FOTOGRAFIA, MONITOREO
}
