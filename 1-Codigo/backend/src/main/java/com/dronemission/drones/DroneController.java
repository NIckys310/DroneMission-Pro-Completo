// ============================================================================================
// DOCUMENTACIÓN · drones/DroneController.java
//
// QUÉ ES:        La puerta HTTP de drones (/api/drones).
// QUÉ HACE:      Listar, ver, crear, editar, cambiar estado, ver historial y borrar;
//                delega en DroneService.
// SI LO CAMBIAS: El permiso de cada acción está en su @PreAuthorize.
// ============================================================================================
package com.dronemission.drones;

import com.dronemission.drones.dto.DroneHistoryResponse;
import com.dronemission.drones.dto.DroneRequest;
import com.dronemission.drones.dto.DroneResponse;
import com.dronemission.drones.dto.DroneStatusRequest;
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
@RequestMapping("/api/drones")
@RequiredArgsConstructor
@Tag(name = "Drones")
public class DroneController {

    /** Dependencia inyectada por Spring (por constructor): servicio de drone. */
    private final DroneService droneService;

    @GetMapping
    @PreAuthorize("hasAuthority('DRONES_VER')")
    @Operation(summary = "Listar drones (búsqueda por texto y filtros por estado/tipo)")
    public List<DroneResponse> findAll(@RequestParam(required = false) String q,
                                       @RequestParam(required = false) DroneStatus status,
                                       @RequestParam(required = false) DroneType type) {
        return droneService.findAll(q, status, type);
    }

    /**
     * Busca drone por identificador.
     * Endpoint GET /api/drones/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return DroneResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DRONES_VER')")
    public DroneResponse findById(@PathVariable Long id) {
        return droneService.findById(id);
    }

    /**
     * Historial de drone.
     * Endpoint GET /api/drones/{id}/history (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return DroneHistoryResponse
     */
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAuthority('DRONES_VER')")
    @Operation(summary = "Historial de misiones y mantenimientos del drone")
    public DroneHistoryResponse history(@PathVariable Long id) {
        return droneService.history(id);
    }

    /**
     * Crea drone.
     * Endpoint POST /api/drones (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_GESTIONAR').
     * @param request petición (DroneRequest)
     * @return DroneResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('DRONES_GESTIONAR')")
    public DroneResponse create(@Valid @RequestBody DroneRequest request) {
        return droneService.create(request);
    }

    /**
     * Actualiza drone.
     * Endpoint PUT /api/drones/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (DroneRequest)
     * @return DroneResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DRONES_GESTIONAR')")
    public DroneResponse update(@PathVariable Long id, @Valid @RequestBody DroneRequest request) {
        return droneService.update(id, request);
    }

    /**
     * Cambia estado.
     * Endpoint PATCH /api/drones/{id}/status (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_ESTADO_TECNICO').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (DroneStatusRequest)
     * @return DroneResponse
     */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('DRONES_ESTADO_TECNICO')")
    @Operation(summary = "Cambiar el estado técnico (DISPONIBLE / FUERA_DE_SERVICIO)")
    public DroneResponse changeStatus(@PathVariable Long id, @Valid @RequestBody DroneStatusRequest request) {
        return droneService.changeTechnicalStatus(id, request);
    }

    /**
     * Carga drone.
     * Endpoint POST /api/drones/{id}/charge (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_ESTADO_TECNICO').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return DroneResponse
     */
    @PostMapping("/{id}/charge")
    @PreAuthorize("hasAuthority('DRONES_ESTADO_TECNICO')")
    @Operation(summary = "Cargar la batería del drone al 100 %")
    public DroneResponse charge(@PathVariable Long id) {
        return droneService.chargeBattery(id);
    }

    /**
     * Desactiva (baja lógica) drone.
     * Endpoint DELETE /api/drones/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DRONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DRONES_GESTIONAR')")
    @Operation(summary = "Desactivar drone (baja lógica)")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        droneService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
