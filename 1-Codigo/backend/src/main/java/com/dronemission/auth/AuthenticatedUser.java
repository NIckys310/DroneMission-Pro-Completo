// ============================================================================================
// DOCUMENTACIÓN · auth/AuthenticatedUser.java
//
// QUÉ ES:        La "credencial" del usuario que hizo la petición.
// QUÉ HACE:      El filtro JWT la crea con el id, el rol y los permisos; los servicios
//                la leen para saber quién es.
// SI LO CAMBIAS: Si le agregas un dato, agrégalo también donde se crea
//                (JwtAuthenticationFilter).
// ============================================================================================
package com.dronemission.auth;

import com.dronemission.users.RoleName;

import java.util.Set;

/**
 * Usuario autenticado de la petición actual (se guarda en el SecurityContext).
 * Es inmutable y no depende de la base de datos.
 */
public record AuthenticatedUser(Long id, String email, String fullName, RoleName role, Set<String> permissions) {

    /**
     * Indica si tiene rol.
     * @param roleName nombre de rol (RoleName)
     * @return boolean
     */
    public boolean hasRole(RoleName roleName) {
        return role == roleName;
    }

    /**
     * Indica si es administrador.
     * @return boolean
     */
    public boolean isAdmin() {
        return role == RoleName.ADMINISTRADOR;
    }
}
