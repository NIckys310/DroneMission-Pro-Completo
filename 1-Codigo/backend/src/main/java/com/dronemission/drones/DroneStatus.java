// ============================================================================================
// DOCUMENTACIÓN · drones/DroneStatus.java
//
// QUÉ ES:        Los estados posibles de un drone.
// QUÉ HACE:      DISPONIBLE, EN_MISION, EN_MANTENIMIENTO, FUERA_DE_SERVICIO.
// SI LO CAMBIAS: Si agregas uno, agrégalo en el frontend: types/index.ts (DroneStatus)
//                y utils/labels.ts (droneStatus).
// ============================================================================================
package com.dronemission.drones;

public enum DroneStatus {
    DISPONIBLE, EN_MISION, EN_MANTENIMIENTO, FUERA_DE_SERVICIO
}
