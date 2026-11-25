// ============================================================================================
// DOCUMENTACIÓN · missions/strategy/AbstractMissionStrategy.java
//
// QUÉ ES:        PATRÓN STRATEGY: la base común de las estrategias.
// QUÉ HACE:      Pone el despegue y el aterrizaje en el punto base; cada estrategia
//                solo arma la parte del medio.
// SI LO CAMBIAS: Si cambias el despegue/aterrizaje aquí, cambia en TODOS los tipos de
//                misión.
// ============================================================================================
package com.dronemission.missions.strategy;

import com.dronemission.missions.WaypointAction;

import java.util.ArrayList;
import java.util.List;

/** Código común a todas las estrategias: despegue y aterrizaje en el punto base. */
public abstract class AbstractMissionStrategy implements MissionStrategy {

    /**
     * Genera plan de vuelo (lista de puntos embebida en la misión).
     * Implementa un método de la interfaz o clase padre.
     * @param center centro (GeoPoint)
     * @param altitudeM altitud (metros) (int)
     * @return lista de PlannedWaypoint
     */
    @Override
    public List<PlannedWaypoint> generateFlightPlan(GeoPoint center, int altitudeM) {
        List<PlannedWaypoint> plan = new ArrayList<>();
        plan.add(new PlannedWaypoint(center, 0, WaypointAction.DESPEGUE));
        plan.addAll(generatePattern(center, altitudeM));
        plan.add(new PlannedWaypoint(center, 0, WaypointAction.ATERRIZAJE));
        return plan;
    }

    /** Recorrido propio de cada tipo de misión (entre el despegue y el aterrizaje). */
    protected abstract List<PlannedWaypoint> generatePattern(GeoPoint center, int altitudeM);
}
