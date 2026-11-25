// ============================================================================================
// DOCUMENTACIÓN · users/dto/UserResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (UserResponse) sobre los usuarios,
//                roles y permisos.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.users.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String username,
        String email,
        String role,
        boolean active,
        boolean pendingApproval,
        LocalDateTime lockedUntil,
        Long pilotId,
        Long clientId,
        LocalDateTime lastLoginAt,
        LocalDateTime createdAt
) {
}
