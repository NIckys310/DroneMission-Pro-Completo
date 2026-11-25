// ============================================================================================
// DOCUMENTACIÓN · users/dto/PermissionResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (PermissionResponse) sobre los
//                usuarios, roles y permisos.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.users.dto;

public record PermissionResponse(Long id, String code, String description) {
}
