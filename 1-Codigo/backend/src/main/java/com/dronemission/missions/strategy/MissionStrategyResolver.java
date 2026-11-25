// ============================================================================================
// DOCUMENTACIÓN · missions/strategy/MissionStrategyResolver.java
//
// QUÉ ES:        PATRÓN STRATEGY: el que elige la estrategia correcta.
// QUÉ HACE:      Recibe el tipo de misión y devuelve la estrategia que lo atiende.
//                Spring le inyecta todas solas.
// SI LO CAMBIAS: No se toca para agregar un tipo: basta con crear la clase @Component
//                nueva.
// ============================================================================================
package com.dronemission.missions.strategy;

import com.dronemission.missions.MissionType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Contexto del patrón Strategy: devuelve la estrategia adecuada para un tipo de misión.
 * Spring inyecta todas las implementaciones de MissionStrategy; agregar un nuevo
 * comportamiento solo requiere crear una nueva clase @Component.
 */
@Component
public class MissionStrategyResolver {

    /** Dependencia inyectada por Spring (por constructor): map. */
    private final Map<MissionType, MissionStrategy> strategies = new EnumMap<>(MissionType.class);

    /**
     * Constructor de MissionStrategyResolver.
     * @param available disponibles (lista de MissionStrategy)
     */
    public MissionStrategyResolver(List<MissionStrategy> available) {
        for (MissionStrategy strategy : available) {
            strategy.supportedTypes().forEach(type -> strategies.put(type, strategy));
        }
        for (MissionType type : MissionType.values()) {
            if (!strategies.containsKey(type)) {
                throw new IllegalStateException("No hay una estrategia para el tipo de misión " + type);
            }
        }
    }

    /**
     * Elige según tipo.
     * @param type tipo (MissionType)
     * @return MissionStrategy
     */
    public MissionStrategy forType(MissionType type) {
        return strategies.get(type);
    }
}
