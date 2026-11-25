// ============================================================================================
// DOCUMENTACIÓN · telemetry/TelemetryController.java
//
// QUÉ ES:        La puerta HTTP de la telemetría.
// QUÉ HACE:      Devuelve las lecturas de una misión (el frontend la consulta cada 2 s
//                mientras vuela).
// SI LO CAMBIAS: Si cambias la ruta, cambia missionApi.telemetry en el frontend.
// ============================================================================================
package com.dronemission.telemetry;

import com.dronemission.telemetry.dto.TelemetryFeed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions/{missionId}/telemetry")
@RequiredArgsConstructor
@Tag(name = "Telemetría simulada")
public class TelemetryController {

    /** Dependencia inyectada por Spring (por constructor): servicio de telemetría. */
    private final TelemetryService telemetryService;

    /**
     * Entrega las lecturas de telemetría.
     * Endpoint GET /api/missions/{missionId}/telemetry (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param missionId identificador de misión (Long)
     * @return TelemetryFeed
     */
    @GetMapping
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    @Operation(summary = "Telemetría (SIMULADA) de la misión; el frontend la consulta cada pocos segundos")
    public TelemetryFeed feed(@PathVariable Long missionId) {
        return telemetryService.feed(missionId);
    }
}
