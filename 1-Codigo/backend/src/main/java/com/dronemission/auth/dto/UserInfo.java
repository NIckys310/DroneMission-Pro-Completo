// ============================================================================================
// DOCUMENTACIÓN · auth/dto/UserInfo.java
//
// QUÉ ES:        Un paquete de datos (UserInfo) que viaja entre backend y frontend
//                para el inicio de sesión.
// QUÉ HACE:      Es un "record": solo transporta datos, no tiene lógica.
// SI LO CAMBIAS: Si cambias sus campos, cambia lo mismo en
//                frontend/src/types/index.ts.
// ============================================================================================
package com.dronemission.auth.dto;

import java.util.List;

/** Datos del usuario autenticado que necesita el frontend (menú, permisos, perfil). */
public record UserInfo(Long id, String fullName, String email, String role, List<String> permissions,
                       Long pilotId, Long clientId) {
}
