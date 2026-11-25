// ============================================================================================
// DOCUMENTACIÓN · users/RoleController.java
//
// QUÉ ES:        La puerta HTTP de roles.
// QUÉ HACE:      Lista los roles y edita los permisos de cada uno (solo
//                administrador).
// SI LO CAMBIAS: Los permisos del ADMINISTRADOR no se pueden quitar.
// ============================================================================================
package com.dronemission.users;

import com.dronemission.users.dto.PermissionResponse;
import com.dronemission.users.dto.RolePermissionsRequest;
import com.dronemission.users.dto.RoleResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Roles y permisos")
public class RoleController {

    /** Dependencia inyectada por Spring (por constructor): servicio de rol. */
    private final RoleService roleService;

    /**
     * Roles.
     * Endpoint GET /api/roles (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAnyAuthority('USUARIOS_GESTIONAR', 'ROLES_GESTIONAR').
     * @return lista de RoleResponse
     */
    @GetMapping("/roles")
    @PreAuthorize("hasAnyAuthority('USUARIOS_GESTIONAR', 'ROLES_GESTIONAR')")
    public List<RoleResponse> roles() {
        return roleService.findAll();
    }

    /**
     * Permisos del rol (copiados dentro del documento).
     * Endpoint GET /api/permissions (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('ROLES_GESTIONAR').
     * @return lista de PermissionResponse
     */
    @GetMapping("/permissions")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public List<PermissionResponse> permissions() {
        return roleService.findAllPermissions();
    }

    /**
     * Actualiza permisos del rol (copiados dentro del documento).
     * Endpoint PUT /api/roles/{id}/permissions (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('ROLES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (RolePermissionsRequest)
     * @return RoleResponse
     */
    @PutMapping("/roles/{id}/permissions")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public RoleResponse updatePermissions(@PathVariable Long id, @Valid @RequestBody RolePermissionsRequest request) {
        return roleService.updatePermissions(id, request.permissions());
    }
}
