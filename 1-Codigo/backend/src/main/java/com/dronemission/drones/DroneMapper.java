// ============================================================================================
// DOCUMENTACIÓN · drones/DroneMapper.java
//
// QUÉ ES:        Traductor entre Drone y sus DTOs.
// QUÉ HACE:      Copia los datos de DroneRequest al drone y del drone a DroneResponse.
// SI LO CAMBIAS: Si agregas un campo y no lo pones aquí, no se guarda o no llega a la
//                pantalla.
// ============================================================================================
package com.dronemission.drones;

import com.dronemission.drones.dto.DroneRequest;
import com.dronemission.drones.dto.DroneResponse;

import java.time.LocalDate;

/** Convierte entre la entidad Drone y sus DTOs. */
public final class DroneMapper {

    /**
     * Constructor de DroneMapper.
     */
    private DroneMapper() {
    }

    /** Copia los campos editables (no cambia el estado: eso lo controlan misiones y mantenimientos). */
    public static void apply(DroneRequest req, Drone drone) {
        drone.setName(req.name().trim());
        drone.setManufacturer(req.manufacturer().trim());
        drone.setModel(req.model().trim());
        drone.setSerialNumber(req.serialNumber().trim().toUpperCase());
        drone.setType(req.type());
        if (req.batteryCapacityMah() != null) drone.setBatteryCapacityMah(req.batteryCapacityMah());
        if (req.batteryLevel() != null) drone.setBatteryLevel(req.batteryLevel());
        if (req.flightHours() != null) drone.setFlightHours(req.flightHours());
        if (req.maxSpeedKmh() != null) drone.setMaxSpeedKmh(req.maxSpeedKmh());
        if (req.autonomyMinutes() != null) drone.setAutonomyMinutes(req.autonomyMinutes());
        drone.setAcquiredOn(req.acquiredOn());
        drone.setLastMaintenanceOn(req.lastMaintenanceOn());
        drone.setNextMaintenanceOn(req.nextMaintenanceOn());
        drone.setDescription(req.description());
    }

    /**
     * Convierte a respuesta.
     * @param d d (Drone)
     * @return DroneResponse
     */
    public static DroneResponse toResponse(Drone d) {
        boolean maintenanceDue = d.getNextMaintenanceOn() != null && !d.getNextMaintenanceOn().isAfter(LocalDate.now());
        return new DroneResponse(d.getId(), d.getCode(), d.getName(), d.getManufacturer(), d.getModel(),
                d.getSerialNumber(), d.getType().name(), d.getStatus().name(), d.getBatteryCapacityMah(),
                d.getBatteryLevel(), d.getFlightHours(), d.getMaxSpeedKmh(), d.getAutonomyMinutes(),
                d.getAcquiredOn(), d.getLastMaintenanceOn(), d.getNextMaintenanceOn(), maintenanceDue,
                d.getDescription(), d.isActive());
    }
}
