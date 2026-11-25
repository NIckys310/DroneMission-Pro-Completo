// ============================================================================================
// DOCUMENTACIÓN · simulator/SimulatorService.java
//
// QUÉ ES:        El generador de vuelos de práctica.
// QUÉ HACE:      Arma un plan de vuelo SIN guardar ninguna misión, usando las mismas
//                estrategias que las misiones reales.
// SI LO CAMBIAS: Las coordenadas por defecto (Cali) están en DEFAULT_LATITUDE /
//                DEFAULT_LONGITUDE.
// ============================================================================================
package com.dronemission.simulator;

import com.dronemission.missions.FlightPlanService;
import com.dronemission.missions.Mission;
import com.dronemission.missions.MissionBuilder;
import com.dronemission.missions.MissionType;
import com.dronemission.missions.dto.FlightPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Simulador 3D de práctica: genera un plan de vuelo de demostración SIN guardar
 * ninguna misión. Reutiliza las mismas estrategias (patrón Strategy) que las
 * misiones reales, así el patrón de vuelo del simulador es idéntico al real.
 */
@Service
@RequiredArgsConstructor
public class SimulatorService {

    /** Punto de referencia por defecto (Cali, Colombia). */
    // >> Coordenadas por defecto del simulador (Cali, Colombia). Cámbialas para practicar en otra ciudad.
    public static final double DEFAULT_LATITUDE = 3.4516;
    /** Constante DEFAULT_LONGITUDE: longitud por defecto. */
    public static final double DEFAULT_LONGITUDE = -76.5320;

    /** Dependencia inyectada por Spring (por constructor): servicio de plan de vuelo. */
    private final FlightPlanService flightPlanService;

    /**
     * Plan de práctica.
     * @param type tipo (MissionType)
     * @param altitudeM altitud (metros) (int)
     * @return FlightPlanResponse
     */
    public FlightPlanResponse practicePlan(MissionType type, int altitudeM) {
        MissionBuilder.validateAltitude(altitudeM);
        Mission draft = new Mission();
        draft.setType(type);
        draft.setLatitude(DEFAULT_LATITUDE);
        draft.setLongitude(DEFAULT_LONGITUDE);
        draft.setTargetAltitudeM(altitudeM);
        flightPlanService.generate(draft);
        return flightPlanService.describe(draft);
    }
}
