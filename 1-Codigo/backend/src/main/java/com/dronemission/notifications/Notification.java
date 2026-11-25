// ============================================================================================
// DOCUMENTACIÓN · notifications/Notification.java
//
// QUÉ ES:        El molde de una notificación (la campanita).
// QUÉ HACE:      Para quién es, el mensaje, la misión relacionada y si ya se leyó.
// SI LO CAMBIAS: Campo nuevo: aquí, en NotificationResponse y en NotificationBell.tsx.
// ============================================================================================
package com.dronemission.notifications;

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

/** Notificación dentro de la aplicación (campanita del frontend). */
// MongoDB: cada objeto se guarda como un documento en la colección "notificaciones"
@Document(collection = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("usuario_id")
    private User user;

    /** Atributo title (String): título. */
    @Field("titulo")
    private String title;

    /** Atributo message (String): mensaje. */
    @Field("mensaje")
    private String message;

    // Referencia a otro documento (DBRef). lazy: la misión solo se carga si se usa
    @DBRef(lazy = true)
    @Field("mision_id")
    private Mission mission;

    /** Atributo read (boolean): leída. */
    @Field("leida")
    private boolean read = false;

    /** Atributo date (LocalDateTime): fecha. */
    @Field("fecha")
    private LocalDateTime date = LocalDateTime.now();
}
