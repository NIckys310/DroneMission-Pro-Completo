// ============================================================================================
// DOCUMENTACIÓN · evidence/EvidenceController.java
//
// QUÉ ES:        La puerta HTTP de evidencias.
// QUÉ HACE:      Subir archivo, registrar URL, listar y descargar evidencias de una
//                misión.
// SI LO CAMBIAS: El tamaño máximo de archivo está en application.yml (multipart).
// ============================================================================================
package com.dronemission.evidence;

import com.dronemission.evidence.dto.EvidenceRequest;
import com.dronemission.evidence.dto.EvidenceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Evidencias")
public class EvidenceController {

    /** Dependencia inyectada por Spring (por constructor): servicio de evidencia. */
    private final EvidenceService evidenceService;

    /**
     * Por misión.
     * Endpoint GET /api/missions/{missionId}/evidences (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param missionId identificador de misión (Long)
     * @return lista de EvidenceResponse
     */
    @GetMapping("/missions/{missionId}/evidences")
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    public List<EvidenceResponse> byMission(@PathVariable Long missionId) {
        return evidenceService.findByMission(missionId);
    }

    @PostMapping(value = "/missions/{missionId}/evidences", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVIDENCIAS_REGISTRAR')")
    @Operation(summary = "Registrar evidencia subiendo un archivo (multipart/form-data)")
    public EvidenceResponse upload(@PathVariable Long missionId,
                                   @Valid @ModelAttribute EvidenceRequest request,
                                   @RequestPart(value = "file", required = false) MultipartFile file) {
        return evidenceService.create(missionId, request, file);
    }

    /**
     * Crea con URL.
     * Endpoint POST /api/missions/{missionId}/evidences (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('EVIDENCIAS_REGISTRAR').
     * @param missionId identificador de misión (Long)
     * @param request petición (EvidenceRequest)
     * @return EvidenceResponse
     */
    @PostMapping(value = "/missions/{missionId}/evidences", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('EVIDENCIAS_REGISTRAR')")
    @Operation(summary = "Registrar evidencia con una URL o como observación (JSON)")
    public EvidenceResponse createWithUrl(@PathVariable Long missionId, @Valid @RequestBody EvidenceRequest request) {
        return evidenceService.create(missionId, request, null);
    }

    /**
     * Archivo.
     * Endpoint GET /api/evidences/{id}/file (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_VER').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Resource>
     */
    @GetMapping("/evidences/{id}/file")
    @PreAuthorize("hasAuthority('MISIONES_VER')")
    @Operation(summary = "Descargar el archivo de una evidencia")
    public ResponseEntity<Resource> file(@PathVariable Long id) {
        EvidenceService.StoredFile stored = evidenceService.loadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(stored.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + stored.fileName() + "\"")
                .body(stored.resource());
    }

    /**
     * Elimina evidencia.
     * Endpoint DELETE /api/evidences/{id} (lo llama el frontend desde api/endpoints.ts).
     * Permiso requerido: hasAuthority('MISIONES_GESTIONAR').
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/evidences/{id}")
    @PreAuthorize("hasAuthority('MISIONES_GESTIONAR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        evidenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
