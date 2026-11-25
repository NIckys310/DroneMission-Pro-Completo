// ============================================================================================
// DOCUMENTACIÓN · users/RoleService.java
//
// QUÉ ES:        La lógica de roles.
// QUÉ HACE:      Consulta roles y cambia sus permisos.
// SI LO CAMBIAS: Aquí se protege que al administrador no se le quiten permisos.
// ============================================================================================
package com.dronemission.users;

import com.dronemission.audit.AuditService;
import com.dronemission.common.exception.BusinessRuleException;
import com.dronemission.common.exception.ResourceNotFoundException;
import com.dronemission.users.dto.PermissionResponse;
import com.dronemission.users.dto.RoleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Consulta de roles y edición de los permisos de cada rol. */
@Service
@RequiredArgsConstructor
public class RoleService {

    /** Dependencia inyectada por Spring (por constructor): repositorio de rol. */
    private final RoleRepository roleRepository;
    /** Dependencia inyectada por Spring (por constructor): repositorio de permiso. */
    private final PermissionRepository permissionRepository;
    /** Dependencia inyectada por Spring (por constructor): servicio de auditoría. */
    private final AuditService auditService;

    /**
     * Lista rol.
     * Transaccional: si algo falla, no se guarda nada.
     * @return lista de RoleResponse
     */
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream().map(this::toResponse).toList();
    }

    /**
     * Lista permisos del rol (copiados dentro del documento).
     * Transaccional: si algo falla, no se guarda nada.
     * @return lista de PermissionResponse
     */
    @Transactional(readOnly = true)
    public List<PermissionResponse> findAllPermissions() {
        return permissionRepository.findAllByOrderByIdAsc().stream()
                .map(p -> new PermissionResponse(p.getId(), p.getCode(), p.getDescription())).toList();
    }

    /**
     * Actualiza permisos del rol (copiados dentro del documento).
     * Transaccional: si algo falla, no se guarda nada.
     * @param roleId identificador de rol (Long)
     * @param codes codes (lista de String)
     * @return RoleResponse
     */
    @Transactional
    public RoleResponse updatePermissions(Long roleId, List<String> codes) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol", roleId));
        if (role.getName() == RoleName.ADMINISTRADOR) {
            throw new BusinessRuleException("Los permisos del administrador no se pueden modificar");
        }
        Map<String, Permission> byCode = permissionRepository.findAll().stream()
                .collect(Collectors.toMap(Permission::getCode, Function.identity()));
        Set<Permission> newPermissions = new HashSet<>();
        for (String code : codes) {
            Permission p = byCode.get(code);
            if (p == null) {
                throw new BusinessRuleException("El permiso " + code + " no existe");
            }
            newPermissions.add(p);
        }
        role.setPermissions(newPermissions);
        auditService.log("ACTUALIZAR", "ROL", roleId, "Permisos del rol " + role.getName() + ": " + String.join(", ", codes));
        return toResponse(role);
    }

    /**
     * Convierte a respuesta.
     * @param r r (Role)
     * @return RoleResponse
     */
    private RoleResponse toResponse(Role r) {
        return new RoleResponse(r.getId(), r.getName().name(), r.getDescription(),
                r.getPermissions().stream().map(Permission::getCode).sorted().toList());
    }
}
