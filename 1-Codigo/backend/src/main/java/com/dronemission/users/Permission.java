// ============================================================================================
// DOCUMENTACIÓN · users/Permission.java
//
// QUÉ ES:        El molde de un permiso (ej. MISIONES_GESTIONAR).
// QUÉ HACE:      Un permiso es una acción que se puede dar o quitar a un rol.
// SI LO CAMBIAS: Permiso nuevo: créalo en DataSeeder y úsalo con
//                @PreAuthorize("hasAuthority('CODIGO')").
// ============================================================================================
package com.dronemission.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Permiso atómico del sistema, por ejemplo MISIONES_GESTIONAR. */
// MongoDB: cada objeto se guarda como un documento en la colección "permisos"
@Document(collection = "permisos")
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    // Sin índice único: el permiso también va embebido en cada rol, y un índice único
    // se repetiría entre roles (dos roles pueden tener el mismo permiso)
    @Field("codigo")
    private String code;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;
}
