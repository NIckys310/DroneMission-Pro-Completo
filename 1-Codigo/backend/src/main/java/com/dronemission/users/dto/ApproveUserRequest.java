// ============================================================================================
// DOCUMENTACIÓN · users/dto/ApproveUserRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (ApproveUserRequest) para los
//                usuarios, roles y permisos.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.users.dto;

import com.dronemission.users.RoleName;
import jakarta.validation.constraints.NotNull;

/** El administrador aprueba una cuenta registrada y le asigna su rol. */
public record ApproveUserRequest(
        @NotNull(message = "El rol es obligatorio") RoleName role,
        Long pilotId,
        Long clientId
) {
}
