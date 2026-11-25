// ============================================================================================
// DOCUMENTACIÓN · pilots/PilotController.java
//
// QUÉ ES:        La puerta HTTP de pilotos (/api/pilots).
// QUÉ HACE:      Listar, ver, crear, editar, historial y "mi perfil".
// SI LO CAMBIAS: El permiso de cada acción está en su @PreAuthorize.
// ============================================================================================
package com.dronemission.pilots;

import com.dronemission.pilots.dto.PilotMissionEntry;
import com.dronemission.pilots.dto.PilotRequest;
import com.dronemission.pilots.dto.PilotResponse;
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
@RequestMapping("/api/pilots")
@RequiredArgsConstructor
@Tag(name = "Pilotos")
public class PilotController {

    /** Dependencia inyectada por Spring (por constructor): servicio de piloto. */
    private final PilotService pilotService;

    @GetMapping
    @PreAuthorize("hasAuthority('PILOTOS_VER')")
    public List<PilotResponse> findAll(@RequestParam(required = false) String q,
                                       @RequestParam(required = false) PilotStatus status) {
        return pilotService.findAll(q, status);
    }

    /**
     * Datos del usuario actual.
     * Endpoint GET /api/pilots/me (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasRole('PILOTO').
     * @return PilotResponse
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('PILOTO')")
    @Operation(summary = "Perfil del piloto autenticado")
    public PilotResponse me() {
        return pilotService.findMine();
    }

    /**
     * Busca piloto por identificador.
     * Endpoint GET /api/pilots/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('PILOTOS_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return PilotResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PILOTOS_VER')")
    public PilotResponse findById(@PathVariable Long id) {
        return pilotService.findById(id);
    }

    /**
     * Misiones.
     * Endpoint GET /api/pilots/{id}/missions (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('PILOTOS_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return lista de PilotMissionEntry
     */
    @GetMapping("/{id}/missions")
    @PreAuthorize("hasAuthority('PILOTOS_VER')")
    @Operation(summary = "Historial de misiones del piloto")
    public List<PilotMissionEntry> missions(@PathVariable Long id) {
        return pilotService.missionsOf(id);
    }

    /**
     * Crea piloto.
     * Endpoint POST /api/pilots (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('PILOTOS_GESTIONAR').
     * @param request petición (PilotRequest)
     * @return PilotResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('PILOTOS_GESTIONAR')")
    public PilotResponse create(@Valid @RequestBody PilotRequest request) {
        return pilotService.create(request);
    }

    /**
     * Actualiza piloto.
     * Endpoint PUT /api/pilots/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('PILOTOS_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @param request petición (PilotRequest)
     * @return PilotResponse
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PILOTOS_GESTIONAR')")
    public PilotResponse update(@PathVariable Long id, @Valid @RequestBody PilotRequest request) {
        return pilotService.update(id, request);
    }

    /**
     * Desactiva (baja lógica) piloto.
     * Endpoint DELETE /api/pilots/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('PILOTOS_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PILOTOS_GESTIONAR')")
    @Operation(summary = "Desactivar piloto")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        pilotService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
