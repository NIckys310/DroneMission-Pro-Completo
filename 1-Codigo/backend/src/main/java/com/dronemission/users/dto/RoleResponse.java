// ============================================================================================
// DOCUMENTACIÓN · users/dto/RoleResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (RoleResponse) sobre los usuarios,
//                roles y permisos.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.users.dto;

import java.util.List;

public record RoleResponse(Long id, String name, String description, List<String> permissions) {
}
