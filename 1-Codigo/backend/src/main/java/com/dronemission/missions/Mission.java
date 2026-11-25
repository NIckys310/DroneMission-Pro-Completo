// ============================================================================================
// DOCUMENTACIÓN · missions/Mission.java
//
// QUÉ ES:        El molde de una misión: la entidad central (colección "misiones").
// QUÉ HACE:      Guarda todos los datos de la misión y su estado. Solo cambia de
//                estado con changeStatus(), que pregunta a MissionStatus si el paso es
//                válido.
// SI LO CAMBIAS: Campo nuevo: aquí + MissionRequest/Response + MissionMapper +
//                MissionBuilder (si es obligatorio) + frontend (types y
//                missionForm.ts).
// ============================================================================================
package com.dronemission.missions;

import com.dronemission.clients.Client;
import com.dronemission.common.exception.InvalidStateTransitionException;
import com.dronemission.users.User;
import com.dronemission.common.persistence.BeforeCreate;
import com.dronemission.common.persistence.BeforeUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Misión de vuelo: entidad central del sistema. Se construye con MissionBuilder. */
// MongoDB: cada objeto se guarda como un documento en la colección "misiones"
@Document(collection = "misiones")
@Getter
@Setter
@NoArgsConstructor
public class Mission {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    /** Atributo code (String): código legible (por ejemplo, DRN-001 o MSN-0001). Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("codigo")
    private String code;

    /** Atributo name (String): nombre. */
    @Field("nombre")
    private String name;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;
    /** Atributo type (MissionType): tipo. */
    @Field("tipo")
    private MissionType type;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("cliente_id")
    private Client client;

    /** Atributo location (String): ubicación. */
    @Field("ubicacion")
    private String location;

    /** Atributo latitude (Double): latitud. */
    @Field("latitud")
    private Double latitude;

    /** Atributo longitude (Double): longitud. */
    @Field("longitud")
    private Double longitude;

    /** Atributo targetAltitudeM (Integer): altitud objetivo del vuelo (metros, 5 a 120). */
    @Field("altitud_objetivo_m")
    private Integer targetAltitudeM;

    /** Atributo scheduledDate (LocalDate): fecha programada. */
    @Field("fecha_programada")
    private LocalDate scheduledDate;

    /** Atributo startTime (LocalTime): hora de inicio. */
    @Field("hora_inicio")
    private LocalTime startTime;

    /** Atributo estimatedEndTime (LocalTime): hora estimada de fin. */
    @Field("hora_fin_estimada")
    private LocalTime estimatedEndTime;
    /** Atributo priority (MissionPriority): prioridad. */
    @Field("prioridad")
    private MissionPriority priority;
    /** Atributo status (MissionStatus): estado. */
    @Field("estado")
    private MissionStatus status = MissionStatus.BORRADOR;

    /** Atributo objective (String): objetivo. */
    @Field("objetivo")
    private String objective;

    /** Atributo observations (String): observaciones. */
    @Field("observaciones")
    private String observations;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("creado_por")
    private User createdBy;

    /** Atributo actualStart (LocalDateTime): fecha y hora real de despegue. */
    @Field("inicio_real")
    private LocalDateTime actualStart;

    /** Atributo actualEnd (LocalDateTime): fecha y hora real de finalización. */
    @Field("fin_real")
    private LocalDateTime actualEnd;

    // Historial de asignaciones (drone + piloto). Cada asignación es un documento de la colección
    // "asignaciones_mision"; aquí se guarda la lista de referencias en orden de asignación.
    @DBRef
    @Field("asignaciones")
    private List<MissionAssignment> assignments = new ArrayList<>();

    // Plan de vuelo EMBEBIDO en la misión (composición): los puntos se guardan dentro del mismo documento
    @Field("plan_vuelo")
    private List<Waypoint> flightPlan = new ArrayList<>();

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt;

    /** Atributo updatedAt (LocalDateTime): fecha y hora de la última actualización. */
    @Field("actualizado_en")
    private LocalDateTime updatedAt;

    // Se ejecuta antes de guardar un documento nuevo (lo llama MongoLifecycleListener)
    @BeforeCreate
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    // Se ejecuta antes de actualizar un documento existente (lo llama MongoLifecycleListener)
    @BeforeUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /** Cambia el estado validando la máquina de estados. */
    public void changeStatus(MissionStatus target) {
        assertCanTransitionTo(target);
        this.status = target;
    }

    /** Lanza una excepción si la transición no está permitida (sin cambiar el estado). */
    public void assertCanTransitionTo(MissionStatus target) {
        if (status == MissionStatus.CANCELADA) {
            throw new InvalidStateTransitionException("La misión " + code + " está cancelada: no puede reabrirse ni iniciarse");
        }
        if (!status.canTransitionTo(target)) {
            throw new InvalidStateTransitionException(
                    "No se puede pasar la misión " + code + " de " + status + " a " + target);
        }
    }

    /** Asignación vigente (drone + piloto), si existe. */
    public Optional<MissionAssignment> getActiveAssignment() {
        return assignments.stream().filter(MissionAssignment::isActive).reduce((first, second) -> second);
    }

    /**
     * Reemplaza plan de vuelo (lista de puntos embebida en la misión).
     * @param waypoints puntos de ruta (lista de Waypoint)
     */
    public void replaceFlightPlan(List<Waypoint> waypoints) {
        flightPlan.clear();
        // Los puntos van embebidos en la misión, por eso no necesitan referencia a ella
        flightPlan.addAll(waypoints);
    }

    /**
     * Agrega observación.
     * @param text text (String)
     */
    public void appendObservation(String text) {
        if (text == null || text.isBlank()) {
            return;
        }
        observations = (observations == null || observations.isBlank()) ? text.trim() : observations + "\n" + text.trim();
        if (observations.length() > 1000) {
            observations = observations.substring(observations.length() - 1000);
        }
    }
}
