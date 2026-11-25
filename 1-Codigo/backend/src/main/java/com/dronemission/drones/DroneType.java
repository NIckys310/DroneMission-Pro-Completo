// ============================================================================================
// DOCUMENTACIÓN · drones/DroneType.java
//
// QUÉ ES:        Los tipos de drone.
// QUÉ HACE:      INSPECCION, AGRICULTURA, VIGILANCIA, TOPOGRAFIA, FOTOGRAFIA.
// SI LO CAMBIAS: Tipo nuevo: valor aquí + línea en DroneFactory.SPECS + frontend
//                (types/index.ts y labels.ts) + revisar compatibleDroneTypes() de las
//                estrategias.
// ============================================================================================
package com.dronemission.drones;

public enum DroneType {
    INSPECCION, AGRICULTURA, VIGILANCIA, TOPOGRAFIA, FOTOGRAFIA
}
