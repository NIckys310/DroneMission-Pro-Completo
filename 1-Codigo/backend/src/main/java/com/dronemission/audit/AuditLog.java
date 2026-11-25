// ============================================================================================
// DOCUMENTACIÓN · audit/AuditLog.java
//
// QUÉ ES:        El molde de un registro de la bitácora (colección "auditoria" en
//                MongoDB).
// QUÉ HACE:      Guarda quién hizo la acción, sobre qué entidad, qué hizo y cuándo.
// SI LO CAMBIAS: Si agregas un campo aquí, agrégalo también en AuditResponse y en
//                AuditService para que se vea.
// ============================================================================================
package com.dronemission.audit;

import com.dronemission.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Registro de auditoría: quién hizo qué, sobre qué entidad y cuándo. */
// MongoDB: cada objeto se guarda como un documento en la colección "auditoria"
@Document(collection = "auditoria")
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("usuario_id")
    private User user;

    /** Atributo userEmail (String): correo del usuario que hizo la acción. */
    @Field("correo_usuario")
    private String userEmail;

    /** Atributo action (String): acción. */
    @Field("accion")
    private String action;

    /** Atributo entity (String): entidad. */
    @Field("entidad")
    private String entity;

    /** Atributo entityId (Long): id de la entidad afectada. */
    @Field("entidad_id")
    private Long entityId;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;

    /** Atributo date (LocalDateTime): fecha. */
    @Field("fecha")
    private LocalDateTime date = LocalDateTime.now();
}
