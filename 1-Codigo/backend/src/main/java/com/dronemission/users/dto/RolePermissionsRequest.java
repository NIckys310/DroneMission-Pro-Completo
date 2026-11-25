// ============================================================================================
// DOCUMENTACIÓN · users/dto/RolePermissionsRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (RolePermissionsRequest) para los
//                usuarios, roles y permisos.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.users.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RolePermissionsRequest(@NotNull(message = "La lista de permisos es obligatoria") List<String> permissions) {
}
