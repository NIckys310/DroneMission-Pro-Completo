// ============================================================================================
// DOCUMENTACIÓN · auth/CurrentUserService.java
//
// QUÉ ES:        Un atajo para saber "quién soy" dentro del backend.
// QUÉ HACE:      Saca al usuario actual del SecurityContext para que los servicios no
//                repitan ese código.
// SI LO CAMBIAS: Se usa en muchos servicios: si cambias el nombre de un método, el
//                compilador te marca todos los usos.
// ============================================================================================
package com.dronemission.auth;

import com.dronemission.common.exception.ResourceNotFoundException;
import com.dronemission.users.User;
import com.dronemission.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Acceso cómodo al usuario que hace la petición actual. */
@Service
@RequiredArgsConstructor
public class CurrentUserService {

    /** Dependencia inyectada por Spring (por constructor): repositorio de usuario. */
    private final UserRepository userRepository;

    /**
     * Busca usuario actual.
     * @return opcional: AuthenticatedUser
     */
    public Optional<AuthenticatedUser> find() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof AuthenticatedUser user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * Obtiene usuario actual.
     * @return AuthenticatedUser
     */
    public AuthenticatedUser get() {
        return find().orElseThrow(() -> new IllegalStateException("No hay un usuario autenticado"));
    }

    /**
     * Obtiene entidad.
     * @return User
     */
    public User getEntity() {
        Long id = get().id();
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
    }
}
