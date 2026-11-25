// ============================================================================================
// DOCUMENTACIÓN · missions/MissionController.java
//
// QUÉ ES:        La puerta HTTP de misiones (/api/missions).
// QUÉ HACE:      Un endpoint por acción: crear, editar, asignar, enviar, preparar,
//                despegar, pausar, reanudar, finalizar, cancelar, plan de vuelo,
//                telemetría...
// SI LO CAMBIAS: Acción nueva: método aquí con @PostMapping y @PreAuthorize, y su
//                función en api/endpoints.ts del frontend.
// ============================================================================================
package com.dronemission.missions;

import com.dronemission.audit.dto.AuditResponse;
import com.dronemission.missions.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de misiones. Los controllers no contienen reglas de negocio:
 * solo reciben la petición, delegan en el servicio y devuelven el DTO.
 */
@RestController
@RequestMapping("/api/missions")
@RequiredArgsConstructor
@Tag(name = "Misiones")
public class MissionController {

    /** Dependencia inyectada por Spring (por constructor): servicio de misión. */
    private final MissionService missionService;
    /** Dependencia inyectada por Spring (por constructor): servicio de ciclo de vida de misión. */
    private final MissionLifecycleService lifecycleService;
    /** Dependencia inyectada por Spring (por constructor): servicio de asignación. */
    private final AssignmentService assignmentService;

    // ----------------------------------------------------------- consultas y CRUD

    @GetMapping
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    @Operation(summary = "Listar misiones (el piloto y el cliente solo ven las suyas)")
    public List<MissionResponse> findAll(@RequestParam(required = false) MissionStatus status,
                                         @RequestParam(required = false) MissionType type,
                                         @RequestParam(required = false) Long clientId,
                                         @RequestParam(required = false) String q) {
        return missionService.findAll(status, type, clientId, q);
    }

    /**
     * Busca misión por identificador.
     * Endpoint GET /api/missions/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MissionResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    public MissionResponse findById(@PathVariable Long id) {
        return missionService.findById(id);
    }

    /**
     * Crea misión.
     * Endpoint POST /api/missions (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param request petición (MissionRequest)
     * @return MissionResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "Crear misión (queda en BORRADOR con su plan de vuelo generado)")
    public MissionResponse create(@Valid @RequestBody MissionRequest request) {
        return missionService.create(request);
    }

    /**
     * Actualiza misión.
     * Endpoint PUT /api/missions/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (MissionRequest)
     * @return MissionResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    public MissionResponse update(@PathVariable Long id, @Valid @RequestBody MissionRequest request) {
        return missionService.update(id, request);
    }

    /**
     * Elimina misión.
     * Endpoint DELETE /api/missions/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "Eliminar misión (solo en BORRADOR)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Plan de vuelo (lista de puntos embebida en la misión).
     * Endpoint GET /api/missions/{id}/flight-plan (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return FlightPlanResponse
     */
    @GetMapping("/{id}/flight-plan")
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    @Operation(summary = "Plan de vuelo programado (puntos de ruta y estimaciones)")
    public FlightPlanResponse flightPlan(@PathVariable Long id) {
        return missionService.flightPlan(id);
    }

    /**
     * Historial de misión.
     * Endpoint GET /api/missions/{id}/history (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return lista de AuditResponse
     */
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    @Operation(summary = "Historial (auditoría) de la misión")
    public List<AuditResponse> history(@PathVariable Long id) {
        return missionService.history(id);
    }

    /**
     * Agrega observación.
     * Endpoint POST /api/missions/{id}/observations (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAnyAuthority('MISIONES_EJECUTAR', 'MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (ObservationRequest)
     * @return MissionResponse
     */
    @PostMapping("/{id}/observations")
    @PreAuthorize("hasAnyAuthority('MISIONES_EJECUTAR', 'MISIONES_GESTIONAR')")
    public MissionResponse addObservation(@PathVariable Long id, @Valid @RequestBody ObservationRequest request) {
        return missionService.addObservation(id, request.text());
    }

    // ----------------------------------------------------------- planificación (operador)

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "BORRADOR -> PENDIENTE")
    public MissionResponse submit(@PathVariable Long id) {
        return lifecycleService.submit(id);
    }

    /**
     * Opciones de asignación.
     * Endpoint GET /api/missions/{id}/assignment-options (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return AssignmentOptionsResponse
     */
    @GetMapping("/{id}/assignment-options")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "Drones y pilotos elegibles para la misión (con el motivo de los no elegibles)")
    public AssignmentOptionsResponse assignmentOptions(@PathVariable Long id) {
        return assignmentService.options(id);
    }

    /**
     * Asigna misión.
     * Endpoint POST /api/missions/{id}/assign (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (AssignRequest)
     * @return MissionResponse
     */
    @PostMapping("/{id}/assign")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "Asignar drone y piloto (PENDIENTE -> PROGRAMADA)")
    public MissionResponse assign(@PathVariable Long id, @Valid @RequestBody AssignRequest request) {
        return assignmentService.assign(id, request);
    }

    /**
     * Cancela misión.
     * Endpoint POST /api/missions/{id}/cancel (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (CancelRequest)
     * @return MissionResponse
     */
    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    @Operation(summary = "Cancelar misión (antes del vuelo)")
    public MissionResponse cancel(@PathVariable Long id, @Valid @RequestBody CancelRequest request) {
        return lifecycleService.cancel(id, request.reason());
    }

    // ----------------------------------------------------------- ejecución (piloto)

    @PostMapping("/{id}/prepare")
    @PreAuthorize("hasAuthority('MISIONES_EJECUTAR')")
    @Operation(summary = "Chequeo prevuelo: PROGRAMADA -> EN_PREPARACION")
    public MissionResponse prepare(@PathVariable Long id) {
        return lifecycleService.prepare(id);
    }

    /**
     * Inicia misión.
     * Endpoint POST /api/missions/{id}/start (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_EJECUTAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MissionResponse
     */
    @PostMapping("/{id}/start")
    @PreAuthorize("hasAuthority('MISIONES_EJECUTAR')")
    @Operation(summary = "Despegar: EN_PREPARACION -> EN_VUELO")
    public MissionResponse start(@PathVariable Long id) {
        return lifecycleService.start(id);
    }

    @PostMapping("/{id}/pause")
    @PreAuthorize("hasAuthority('MISIONES_EJECUTAR')")
    @Operation(summary = "EN_VUELO -> PAUSADA")
    public MissionResponse pause(@PathVariable Long id, @RequestBody(required = false) ObservationRequest request) {
        return lifecycleService.pause(id, request != null ? request.text() : null);
    }

    /**
     * Reanuda misión.
     * Endpoint POST /api/missions/{id}/resume (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_EJECUTAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return MissionResponse
     */
    @PostMapping("/{id}/resume")
    @PreAuthorize("hasAuthority('MISIONES_EJECUTAR')")
    @Operation(summary = "PAUSADA -> EN_VUELO")
    public MissionResponse resume(@PathVariable Long id) {
        return lifecycleService.resume(id);
    }

    @PostMapping("/{id}/finish")
    @PreAuthorize("hasAuthority('MISIONES_EJECUTAR')")
    @Operation(summary = "Finalizar: EN_VUELO | PAUSADA -> FINALIZADA (genera el reporte)")
    public MissionResponse finish(@PathVariable Long id, @RequestBody(required = false) ObservationRequest request) {
        return lifecycleService.finish(id, request != null ? request.text() : null);
    }
}
