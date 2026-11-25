// ============================================================================================
// DOCUMENTACIÓN · telemetry/Telemetry.java
//
// QUÉ ES:        El molde de una lectura de telemetría.
// QUÉ HACE:      Posición, altitud, velocidad, batería, temperatura y distancia en un
//                momento. Todas son simuladas.
// SI LO CAMBIAS: Dato nuevo del vuelo: aquí, en TelemetrySimulator, en TelemetryPoint
//                y en TelemetryPanel.tsx.
// ============================================================================================
package com.dronemission.telemetry;

import com.dronemission.missions.Mission;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Lectura de telemetría. En esta versión TODAS son simuladas (simulated = true). */
// MongoDB: cada objeto se guarda como un documento en la colección "telemetria"
@Document(collection = "telemetria")
@Getter
@Setter
@NoArgsConstructor
public class Telemetry {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef). lazy: la misión solo se carga si se usa
    @DBRef(lazy = true)
    @Field("mision_id")
    private Mission mission;

    /** Atributo recordedAt (LocalDateTime): momento de la lectura. */
    @Field("registrado_en")
    private LocalDateTime recordedAt = LocalDateTime.now();

    /** Atributo latitude (Double): latitud. */
    @Field("latitud")
    private Double latitude;

    /** Atributo longitude (Double): longitud. */
    @Field("longitud")
    private Double longitude;

    /** Atributo altitudeM (Double): altitud (metros). */
    @Field("altitud_m")
    private Double altitudeM;

    /** Atributo speedKmh (Double): velocidad (km/h). */
    @Field("velocidad_kmh")
    private Double speedKmh;

    /** Atributo battery (Integer): batería. */
    @Field("bateria")
    private Integer battery;

    /** Atributo distanceM (Double): distancia recorrida (metros). */
    @Field("distancia_m")
    private Double distanceM;

    /** Atributo temperatureC (Double): temperatura (°C). */
    @Field("temperatura_c")
    private Double temperatureC;

    /** Atributo flightTimeS (Integer): tiempo de vuelo (segundos). */
    @Field("tiempo_vuelo_s")
    private Integer flightTimeS;

    /** Índice del próximo punto del plan de vuelo al que se dirige el drone. */
    @Field("punto_ruta_actual")
    private Integer currentWaypoint;

    /** Atributo simulated (boolean): simulada. */
    @Field("simulado")
    private boolean simulated = true;
}
