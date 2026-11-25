// ============================================================================================
// DOCUMENTACIÓN · maintenance/Maintenance.java
//
// QUÉ ES:        El molde de un mantenimiento de drone.
// QUÉ HACE:      Tipo, estado, fechas, costo, técnico y observaciones.
// SI LO CAMBIAS: Campo nuevo: aquí, en sus DTOs y en MaintenancePage.tsx.
// ============================================================================================
package com.dronemission.maintenance;

import com.dronemission.drones.Drone;
import com.dronemission.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Mantenimiento de un drone realizado por un técnico. */
// MongoDB: cada objeto se guarda como un documento en la colección "mantenimientos"
@Document(collection = "mantenimientos")
@Getter
@Setter
@NoArgsConstructor
public class Maintenance {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("drone_id")
    private Drone drone;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("tecnico_id")
    private User technician;
    /** Atributo type (MaintenanceType): tipo. */
    @Field("tipo")
    private MaintenanceType type;

    /** Atributo date (LocalDate): fecha. */
    @Field("fecha")
    private LocalDate date;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;

    /** Atributo cost (BigDecimal): costo. */
    @Field("costo")
    private BigDecimal cost = BigDecimal.ZERO;
    /** Atributo status (MaintenanceStatus): estado. */
    @Field("estado")
    private MaintenanceStatus status = MaintenanceStatus.PROGRAMADO;

    /** Atributo nextDate (LocalDate): fecha del próximo mantenimiento. */
    @Field("proxima_fecha")
    private LocalDate nextDate;

    /** Atributo observations (String): observaciones. */
    @Field("observaciones")
    private String observations;

    /** Atributo startedAt (LocalDateTime): cuándo se inició. */
    @Field("iniciado_en")
    private LocalDateTime startedAt;

    /** Atributo completedAt (LocalDateTime): cuándo se completó. */
    @Field("completado_en")
    private LocalDateTime completedAt;

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt = LocalDateTime.now();
}
