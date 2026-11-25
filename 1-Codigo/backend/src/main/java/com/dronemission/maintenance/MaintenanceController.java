// ============================================================================================
// DOCUMENTACIÓN · maintenance/MaintenanceController.java
//
// QUÉ ES:        La puerta HTTP de mantenimientos.
// QUÉ HACE:      Programar, iniciar, completar y cancelar mantenimientos.
// SI LO CAMBIAS: El permiso de cada acción está en su @PreAuthorize.
// ============================================================================================
package com.dronemission.maintenance;

import com.dronemission.maintenance.dto.MaintenanceCompleteRequest;
import com.dronemission.maintenance.dto.MaintenanceRequest;
import com.dronemission.maintenance.dto.MaintenanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@Tag(name = "Mantenimiento")
public class MaintenanceController {

    /** Dependencia inyectada por Spring (por constructor): servicio de mantenimiento. */
    private final MaintenanceService maintenanceService;

    @GetMapping
    @PreAuthorize("hasAuthority('MANTENIMIENTO_VER')")
    public List<MaintenanceResponse> findAll(@RequestParam(required = false) MaintenanceStatus status,
                                             @RequestParam(required = false) MaintenanceType type,
                                             @RequestParam(required = false) Long droneId) {
        return maintenanceService.findAll(status, type, droneId);
    }

    /**
     * Busca mantenimiento por identificador.
     * Endpoint GET /api/maintenance/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MANTENIMIENTO_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MaintenanceResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANTENIMIENTO_VER')")
    public MaintenanceResponse findById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    /**
     * Crea mantenimiento.
     * Endpoint POST /api/maintenance (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MANTENIMIENTO_GESTIONAR').
     * @param request petición (MaintenanceRequest)
     * @return MaintenanceResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('MANTENIMIENTO_GESTIONAR')")
    @Operation(summary = "Registrar mantenimiento (startNow=true lo inicia de inmediato)")
    public MaintenanceResponse create(@Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.create(request);
    }

    /**
     * Actualiza mantenimiento.
     * Endpoint PUT /api/maintenance/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MANTENIMIENTO_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (MaintenanceRequest)
     * @return MaintenanceResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANTENIMIENTO_GESTIONAR')")
    public MaintenanceResponse update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequest request) {
        return maintenanceService.update(id, request);
    }

    /**
     * Inicia mantenimiento.
     * Endpoint POST /api/maintenance/{id}/start (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MANTENIMIENTO_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MaintenanceResponse
     */
    @PostMapping("/{id}/start")
    @PreAuthorize("hasAuthority('MANTENIMIENTO_GESTIONAR')")
    @Operation(summary = "Iniciar mantenimiento: el drone pasa a EN_MANTENIMIENTO")
    public MaintenanceResponse start(@PathVariable Long id) {
        return maintenanceService.start(id);
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAuthority('MANTENIMIENTO_GESTIONAR')")
    @Operation(summary = "Completar mantenimiento: el drone vuelve a DISPONIBLE")
    public MaintenanceResponse complete(@PathVariable Long id, @Valid @RequestBody(required = false) MaintenanceCompleteRequest request) {
        return maintenanceService.complete(id, request);
    }

    /**
     * Cancela mantenimiento.
     * Endpoint POST /api/maintenance/{id}/cancel (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MANTENIMIENTO_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MaintenanceResponse
     */
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('MANTENIMIENTO_GESTIONAR')")
    public MaintenanceResponse cancel(@PathVariable Long id) {
        return maintenanceService.cancel(id);
    }
}
