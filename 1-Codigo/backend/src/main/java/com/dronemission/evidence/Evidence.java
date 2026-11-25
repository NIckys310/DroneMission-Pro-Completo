// ============================================================================================
// DOCUMENTACIÓN · evidence/Evidence.java
//
// QUÉ ES:        El molde de una evidencia de misión (foto, video, documento u
//                observación).
// QUÉ HACE:      Guarda tipo, URL o archivo, descripción y si el cliente la puede ver.
// SI LO CAMBIAS: Si cambias FILE_PREFIX, las evidencias ya subidas dejan de
//                encontrarse.
// ============================================================================================
package com.dronemission.evidence;

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

/**
 * Evidencia de una misión. "url" puede ser:
 *  - una URL externa o ruta pública (/demo/...),
 *  - "file:<nombre>" para archivos subidos al almacenamiento local.
 */
// MongoDB: cada objeto se guarda como un documento en la colección "evidencias"
@Document(collection = "evidencias")
@Getter
@Setter
@NoArgsConstructor
public class Evidence {

    /** Constante FILE_PREFIX: prefijo de archivo. */
    public static final String FILE_PREFIX = "file:";

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Referencia a otro documento (DBRef). lazy: la misión solo se carga si se usa
    @DBRef(lazy = true)
    @Field("mision_id")
    private Mission mission;
    /** Atributo type (EvidenceType): tipo. */
    @Field("tipo")
    private EvidenceType type;

    /** Atributo name (String): nombre. */
    @Field("nombre")
    private String name;

    /** Atributo url (String): URL. */
    @Field("url")
    private String url;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;

    /** Atributo visibleToClient (boolean): true si el cliente puede ver esta evidencia. */
    @Field("visible_cliente")
    private boolean visibleToClient = true;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("subido_por")
    private User uploadedBy;

    /** Atributo date (LocalDateTime): fecha. */
    @Field("fecha")
    private LocalDateTime date = LocalDateTime.now();

    /**
     * Indica si es archivo stored.
     * @return boolean
     */
    public boolean isStoredFile() {
        return url != null && url.startsWith(FILE_PREFIX);
    }
}
