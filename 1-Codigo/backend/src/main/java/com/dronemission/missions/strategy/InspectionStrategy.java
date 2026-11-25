// ============================================================================================
// DOCUMENTACIÓN · missions/strategy/InspectionStrategy.java
//
// QUÉ ES:        PATRÓN STRATEGY: cómo vuela una misión de inspección o fotografía.
// QUÉ HACE:      Da órbitas alrededor del objetivo tomando fotos. Define batería
//                mínima (50 %), velocidad (20 km/h) y consumo.
// SI LO CAMBIAS: Cambia el radio, los puntos por órbita, la batería mínima o el
//                consumo aquí mismo.
// ============================================================================================
package com.dronemission.missions.strategy;

import com.dronemission.drones.DroneType;
import com.dronemission.missions.MissionType;
import com.dronemission.missions.WaypointAction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Inspección y fotografía: órbitas alrededor del objetivo tomando fotos. */
@Component
public class InspectionStrategy extends AbstractMissionStrategy {

    /** Constante POINTS_PER_ORBIT: órbita de por de puntos. */
    private static final int POINTS_PER_ORBIT = 8;
    /** Constante RADIUS_M: radio (m). */
    private static final double RADIUS_M = 60;

    @Override public String name() { return "Órbita de inspección"; }
    @Override public Set<MissionType> supportedTypes() { return Set.of(MissionType.INSPECCION, MissionType.FOTOGRAFIA); }
    // >> Batería mínima (%) para aceptar un drone en esta misión, velocidad crucero (km/h) y consumo (% por minuto). Cambia estos números para cambiar la regla.
    @Override public int minimumBatteryPercent() { return 50; }
    @Override public Set<DroneType> compatibleDroneTypes() { return Set.of(DroneType.INSPECCION, DroneType.FOTOGRAFIA, DroneType.TOPOGRAFIA); }
    @Override public double cruiseSpeedKmh() { return 20; }
    @Override public double batteryDrainPerMinute() { return 1.8; }

    /**
     * Genera patrón (recorrido).
     * Implementa un método de la interfaz o clase padre.
     * @param center centro (GeoPoint)
     * @param altitudeM altitud (metros) (int)
     * @return lista de PlannedWaypoint
     */
    @Override
    protected List<PlannedWaypoint> generatePattern(GeoPoint center, int altitudeM) {
        List<PlannedWaypoint> points = new ArrayList<>();
        // Dos órbitas: una a la altitud objetivo y otra 15 m más abajo para ver detalles
        int[] altitudes = {altitudeM, Math.max(10, altitudeM - 15)};
        for (int altitude : altitudes) {
            for (int i = 0; i < POINTS_PER_ORBIT; i++) {
                double angle = 2 * Math.PI * i / POINTS_PER_ORBIT;
                GeoPoint p = center.offset(RADIUS_M * Math.cos(angle), RADIUS_M * Math.sin(angle));
                points.add(new PlannedWaypoint(p, altitude, WaypointAction.FOTO));
            }
        }
        return points;
    }
}
