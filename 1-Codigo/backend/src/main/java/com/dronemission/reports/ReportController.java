// ============================================================================================
// DOCUMENTACIÓN · reports/ReportController.java
//
// QUÉ ES:        La puerta HTTP de reportes (/api/reports).
// QUÉ HACE:      Genera y descarga los PDF de misión, drones, pilotos y mantenimiento.
// SI LO CAMBIAS: Reporte nuevo: endpoint aquí + método en ReportService +
//                PdfReportGenerator + ReportsPage.tsx.
// ============================================================================================
package com.dronemission.reports;

import com.dronemission.reports.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reportes")
public class ReportController {

    /** Dependencia inyectada por Spring (por constructor): servicio de reporte. */
    private final ReportService reportService;

    /**
     * Historial de reporte.
     * Endpoint GET /api/reports (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_VER').
     * @return lista de ReportResponse
     */
    @GetMapping
    @PreAuthorize("hasAuthority('REPORTES_VER')")
    @Operation(summary = "Historial de reportes generados")
    public List<ReportResponse> history() {
        return reportService.history();
    }

    /**
     * Reporte de misión.
     * Endpoint GET /api/reports/missions/{missionId} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_VER').
     * @param missionId identificador de misión (Long)
     * @return MissionReport
     */
    @GetMapping("/missions/{missionId}")
    @PreAuthorize("hasAuthority('REPORTES_VER')")
    public MissionReport missionReport(@PathVariable Long missionId) {
        return reportService.missionReport(missionId);
    }

    /**
     * Pdf de reporte de misión.
     * Endpoint GET /api/reports/missions/{missionId}/pdf (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_VER').
     * @param missionId identificador de misión (Long)
     * @return ResponseEntity<byte[]>
     */
    @GetMapping("/missions/{missionId}/pdf")
    @PreAuthorize("hasAuthority('REPORTES_VER')")
    @Operation(summary = "Descargar el reporte de misión en PDF")
    public ResponseEntity<byte[]> missionReportPdf(@PathVariable Long missionId) {
        return pdf(reportService.missionPdf(missionId));
    }

    /**
     * Reporte de drone.
     * Endpoint GET /api/reports/drones (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return DroneReport
     */
    @GetMapping("/drones")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public DroneReport droneReport() {
        return reportService.droneReport();
    }

    /**
     * Pdf de reporte de drone.
     * Endpoint GET /api/reports/drones/pdf (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return ResponseEntity<byte[]>
     */
    @GetMapping("/drones/pdf")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public ResponseEntity<byte[]> droneReportPdf() {
        return pdf(reportService.dronePdf());
    }

    /**
     * Reporte de piloto.
     * Endpoint GET /api/reports/pilots (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return PilotReport
     */
    @GetMapping("/pilots")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public PilotReport pilotReport() {
        return reportService.pilotReport();
    }

    /**
     * Pdf de reporte de piloto.
     * Endpoint GET /api/reports/pilots/pdf (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return ResponseEntity<byte[]>
     */
    @GetMapping("/pilots/pdf")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public ResponseEntity<byte[]> pilotReportPdf() {
        return pdf(reportService.pilotPdf());
    }

    /**
     * Reporte de mantenimiento.
     * Endpoint GET /api/reports/maintenance (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return MaintenanceReport
     */
    @GetMapping("/maintenance")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public MaintenanceReport maintenanceReport() {
        return reportService.maintenanceReport();
    }

    /**
     * Pdf de reporte de mantenimiento.
     * Endpoint GET /api/reports/maintenance/pdf (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('REPORTES_GENERAR').
     * @return ResponseEntity<byte[]>
     */
    @GetMapping("/maintenance/pdf")
    @PreAuthorize("hasAuthority('REPORTES_GENERAR')")
    public ResponseEntity<byte[]> maintenanceReportPdf() {
        return pdf(reportService.maintenancePdf());
    }

    /**
     * Pdf.
     * @param file archivo (ReportService.PdfFile)
     * @return ResponseEntity<byte[]>
     */
    private ResponseEntity<byte[]> pdf(ReportService.PdfFile file) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.fileName() + "\"")
                .body(file.content());
    }
}
