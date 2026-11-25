// ============================================================================================
// DOCUMENTACIÓN · dashboard/DashboardController.java
//
// QUÉ ES:        La puerta HTTP del dashboard (/api/dashboard).
// QUÉ HACE:      Devuelve los indicadores de la pantalla de inicio.
// SI LO CAMBIAS: Si cambias la ruta, cambia dashboardApi en el frontend.
// ============================================================================================
package com.dronemission.dashboard;

import com.dronemission.dashboard.dto.DashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard")
public class DashboardController {

    /** Dependencia inyectada por Spring (por constructor): servicio de dashboard. */
    private final DashboardService dashboardService;

    /**
     * Dashboard.
     * Endpoint GET /api/dashboard (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('DASHBOARD_VER').
     * @return DashboardResponse
     */
    @GetMapping
    @PreAuthorize("hasAuthority('DASHBOARD_VER')")
    @Operation(summary = "Indicadores, gráficos y alertas de la operación")
    public DashboardResponse dashboard() {
        return dashboardService.build();
    }
}
