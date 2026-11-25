// ============================================================================================
// DOCUMENTACIÓN · users/UserController.java
//
// QUÉ ES:        La puerta HTTP de usuarios (/api/users). Solo administrador.
// QUÉ HACE:      Listar, crear, editar, activar, aprobar cuentas registradas y
//                desbloquear.
// SI LO CAMBIAS: El @PreAuthorize de arriba protege toda la clase.
// ============================================================================================
package com.dronemission.users;

import com.dronemission.users.dto.ApproveUserRequest;
import com.dronemission.users.dto.UserRequest;
import com.dronemission.users.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
@Tag(name = "Usuarios")
public class UserController {

    /** Dependencia inyectada por Spring (por constructor): servicio de usuario. */
    private final UserService userService;

    /**
     * Lista usuario.
     * Endpoint GET /api/users (lo llama el frontend desde api/endpoints.ts).
     * @return lista de UserResponse
     */
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    /**
     * Busca usuario por identificador.
     * Endpoint GET /api/users/{id} (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return UserResponse
     */
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    /**
     * Crea usuario.
     * Endpoint POST /api/users (lo llama el frontend desde api/endpoints.ts).
     * @param request petición (UserRequest)
     * @return UserResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest request) {
        return userService.create(request);
    }

    /**
     * Actualiza usuario.
     * Endpoint PUT /api/users/{id} (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (UserRequest)
     * @return UserResponse
     */
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    /**
     * Aprueba usuario.
     * Endpoint POST /api/users/{id}/approve (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (ApproveUserRequest)
     * @return UserResponse
     */
    @PostMapping("/{id}/approve")
    @Operation(summary = "Aprobar una cuenta registrada y asignarle su rol")
    public UserResponse approve(@PathVariable Long id, @Valid @RequestBody ApproveUserRequest request) {
        return userService.approve(id, request);
    }

    /**
     * Rechaza usuario.
     * Endpoint POST /api/users/{id}/reject (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @PostMapping("/{id}/reject")
    @Operation(summary = "Rechazar (eliminar) una cuenta pendiente")
    public ResponseEntity<Void> reject(@PathVariable Long id) {
        userService.reject(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Desactiva (baja lógica) usuario.
     * Endpoint DELETE /api/users/{id} (lo llama el frontend desde api/endpoints.ts).
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar usuario")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        userService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
