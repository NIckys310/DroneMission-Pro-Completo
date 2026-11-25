// ============================================================================================
// DOCUMENTACIÓN · incidents/Incident.java
//
// QUÉ ES:        El molde de un incidente durante una misión.
// QUÉ HACE:      Tipo, gravedad, estado, descripción y quién lo reportó (null si lo
//                detectó el sistema, como la batería baja).
// SI LO CAMBIAS: Campo nuevo: aquí, en sus DTOs y en incidentForm.ts del frontend.
// ============================================================================================
package com.dronemission.incidents;

import com.dronemission.missions.Mission;
import com.dronemission.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Incidente ocurrido durante una misión. reportedBy es null cuando lo registra el sistema. */
// MongoDB: cada objeto se guarda como un documento en la colección "incidentes"
@Document(collection = "incidentes")
@Getter
@Setter
@NoArgsConstructor
public class Incident {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef). lazy: la misión solo se carga si se usa
    @DBRef(lazy = true)
    @Field("mision_id")
    private Mission mission;

    /** Atributo date (LocalDateTime): fecha. */
    @Field("fecha")
    private LocalDateTime date = LocalDateTime.now();
    /** Atributo type (IncidentType): tipo. */
    @Field("tipo")
    private IncidentType type;
    /** Atributo severity (IncidentSeverity): gravedad. */
    @Field("severidad")
    private IncidentSeverity severity;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("registrado_por")
    private User reportedBy;
    /** Atributo status (IncidentStatus): estado. */
    @Field("estado")
    private IncidentStatus status = IncidentStatus.ABIERTO;

    /** Atributo resolution (String): resolución. */
    @Field("resolucion")
    private String resolution;
}
