// ============================================================================================
// DOCUMENTACIÓN · missions/Waypoint.java
//
// QUÉ ES:        Un punto del plan de vuelo.
// QUÉ HACE:      Latitud, longitud, altitud, orden y acción (foto, fumigación, video).
//                Se guarda DENTRO de la misión en MongoDB.
// SI LO CAMBIAS: Si agregas un campo, agrégalo en WaypointResponse y en
//                FlightPlanPanel.tsx.
// ============================================================================================
package com.dronemission.missions;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Punto del plan de vuelo programado con anticipación.
 * El drone virtual recorre los puntos en orden durante la simulación.
 *
 * MongoDB: NO tiene colección propia. Se guarda EMBEBIDO dentro del documento de la misión
 * (campo "plan_vuelo"), porque un punto de ruta no existe sin su misión (composición).
 */
@Getter
@Setter
@NoArgsConstructor
public class Waypoint {

    /** Atributo sequence (Integer): orden. */
    @Field("orden")
    private Integer sequence;

    /** Atributo latitude (Double): latitud. */
    @Field("latitud")
    private Double latitude;

    /** Atributo longitude (Double): longitud. */
    @Field("longitud")
    private Double longitude;

    /** Atributo altitudeM (Integer): altitud (metros). */
    @Field("altitud_m")
    private Integer altitudeM;
    /** Atributo action (WaypointAction): acción. */
    @Field("accion")
    private WaypointAction action;
}
