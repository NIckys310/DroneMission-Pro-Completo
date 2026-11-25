// ============================================================================================
// DOCUMENTACIÓN · clients/Client.java
//
// QUÉ ES:        El molde de un cliente (colección "clientes").
// QUÉ HACE:      Guarda nombre, documento, contacto, ciudad y si está activo. Un
//                cliente inactivo no puede tener misiones nuevas.
// SI LO CAMBIAS: Campo nuevo: agrégalo aquí, en ClientRequest/ClientResponse, en
//                ClientMapper y en el frontend (types/index.ts y clientForm.ts).
// ============================================================================================
package com.dronemission.clients;

import com.dronemission.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Cliente que contrata misiones. Puede tener un usuario para consultar sus misiones. */
// MongoDB: cada objeto se guarda como un documento en la colección "clientes"
@Document(collection = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;
    /** Atributo type (ClientType): tipo. */
    @Field("tipo")
    private ClientType type;

    /** Atributo name (String): nombre. */
    @Field("nombre")
    private String name;

    /** Atributo document (String): documento. Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("documento")
    private String document;

    /** Atributo email (String): correo. */
    @Field("correo")
    private String email;

    /** Atributo phone (String): teléfono. */
    @Field("telefono")
    private String phone;

    /** Atributo address (String): dirección. */
    @Field("direccion")
    private String address;

    /** Atributo city (String): ciudad. */
    @Field("ciudad")
    private String city;

    /** Atributo active (boolean): true si está activo (baja lógica). */
    @Field("activo")
    private boolean active = true;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("usuario_id")
    private User user;

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt = LocalDateTime.now();
}
