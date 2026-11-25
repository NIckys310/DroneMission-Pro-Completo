// ============================================================================================
// DOCUMENTACIÓN · evidence/EvidenceService.java
//
// QUÉ ES:        La lógica de evidencias.
// QUÉ HACE:      Guarda la evidencia, la liga a la misión y filtra: el cliente solo ve
//                las marcadas como visibles.
// SI LO CAMBIAS: Si quitas el filtro de visibilidad, el cliente vería todas las
//                evidencias.
// ============================================================================================
package com.dronemission.evidence;

import com.dronemission.audit.AuditService;
import com.dronemission.auth.AuthenticatedUser;
import com.dronemission.auth.CurrentUserService;
import com.dronemission.common.exception.BusinessRuleException;
import com.dronemission.common.exception.ResourceNotFoundException;
import com.dronemission.evidence.dto.EvidenceRequest;
import com.dronemission.evidence.dto.EvidenceResponse;
import com.dronemission.missions.Mission;
import com.dronemission.missions.MissionAccessService;
import com.dronemission.missions.MissionRepository;
import com.dronemission.users.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvidenceService {

    /** Dependencia inyectada por Spring (por constructor): repositorio de evidencia. */
    private final EvidenceRepository evidenceRepository;
    /** Dependencia inyectada por Spring (por constructor): repositorio de misión. */
    private final MissionRepository missionRepository;
    /** Dependencia inyectada por Spring (por constructor): servicio de acceso de misión. */
    private final MissionAccessService accessService;
    /** Dependencia inyectada por Spring (por constructor): servicio de almacenamiento de archivo. */
    private final FileStorageService storageService;
    /** Dependencia inyectada por Spring (por constructor): servicio de auditoría. */
    private final AuditService auditService;
    /** Dependencia inyectada por Spring (por constructor): servicio de usuario actual. */
    private final CurrentUserService currentUserService;

    /** Evidencias de una misión. El cliente solo ve las marcadas como visibles para él. */
    @Transactional(readOnly = true)
    public List<EvidenceResponse> findByMission(Long missionId) {
        Mission mission = getMission(missionId);
        accessService.checkView(mission);
        boolean isClient = currentUserService.get().hasRole(RoleName.CLIENTE);
        return evidenceRepository.findByMissionIdOrderByDateDesc(missionId).stream()
                .filter(e -> !isClient || e.isVisibleToClient())
                .map(EvidenceService::toResponse)
                .toList();
    }

    /** Registra una evidencia con archivo subido (file) o con URL. */
    @Transactional
    public EvidenceResponse create(Long missionId, EvidenceRequest req, MultipartFile file) {
        Mission mission = getMission(missionId);
        AuthenticatedUser user = currentUserService.get();
        if (!accessService.canContribute(mission, user, "EVIDENCIAS_REGISTRAR")) {
            throw new AccessDeniedException("No puedes registrar evidencias en la misión " + mission.getCode());
        }
        if (!MissionAccessService.evidenceAllowed(mission.getStatus())) {
            throw new BusinessRuleException("Solo se registran evidencias en misiones en preparación, en vuelo, pausadas o finalizadas");
        }
        String url;
        if (file != null && !file.isEmpty()) {
            url = Evidence.FILE_PREFIX + storageService.store(file);
        } else if (req.type() == EvidenceType.OBSERVACION) {
            url = req.url() == null || req.url().isBlank() ? "-" : req.url().trim();
            if (req.description() == null || req.description().isBlank()) {
                throw new BusinessRuleException("Una observación debe tener descripción");
            }
        } else if (req.url() != null && !req.url().isBlank()) {
            url = req.url().trim();
        } else {
            throw new BusinessRuleException("Adjunta un archivo o indica la URL de la evidencia");
        }

        Evidence evidence = new Evidence();
        evidence.setMission(mission);
        evidence.setType(req.type());
        evidence.setName(req.name().trim());
        evidence.setUrl(url);
        evidence.setDescription(req.description());
        evidence.setVisibleToClient(req.visibleToClient() == null || req.visibleToClient());
        evidence.setUploadedBy(currentUserService.getEntity());
        evidenceRepository.save(evidence);
        auditService.log("REGISTRAR", "EVIDENCIA", evidence.getId(), "Evidencia '" + evidence.getName() + "' en " + mission.getCode());
        return toResponse(evidence);
    }

    /**
     * Carga archivo.
     * Transaccional: si algo falla, no se guarda nada.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return StoredFile
     */
    @Transactional(readOnly = true)
    public StoredFile loadFile(Long id) {
        Evidence evidence = get(id);
        accessService.checkView(evidence.getMission());
        if (currentUserService.get().hasRole(RoleName.CLIENTE) && !evidence.isVisibleToClient()) {
            throw new AccessDeniedException("Evidencia no disponible");
        }
        if (!evidence.isStoredFile()) {
            throw new BusinessRuleException("La evidencia no tiene un archivo almacenado");
        }
        String storedName = evidence.getUrl().substring(Evidence.FILE_PREFIX.length());
        return new StoredFile(storageService.load(storedName), storageService.contentType(storedName), storedName);
    }

    /**
     * Elimina evidencia.
     * Transaccional: si algo falla, no se guarda nada.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     */
    @Transactional
    public void delete(Long id) {
        Evidence evidence = get(id);
        if (!accessService.canManage(currentUserService.get())) {
            throw new AccessDeniedException("Solo operaciones puede eliminar evidencias");
        }
        if (evidence.isStoredFile()) {
            storageService.delete(evidence.getUrl().substring(Evidence.FILE_PREFIX.length()));
        }
        evidenceRepository.delete(evidence);
        auditService.log("ELIMINAR", "EVIDENCIA", id, "Evidencia '" + evidence.getName() + "' eliminada");
    }

    /**
     * Obtiene evidencia.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return Evidence
     */
    private Evidence get(Long id) {
        return evidenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evidencia", id));
    }

    /**
     * Obtiene misión.
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return Mission
     */
    private Mission getMission(Long id) {
        return missionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Misión", id));
    }

    /**
     * Convierte a respuesta.
     * @param e e (Evidence)
     * @return EvidenceResponse
     */
    public static EvidenceResponse toResponse(Evidence e) {
        String url = e.isStoredFile() ? "/api/evidences/" + e.getId() + "/file" : e.getUrl();
        return new EvidenceResponse(e.getId(), e.getMission().getId(), e.getType().name(), e.getName(), url,
                e.isStoredFile(), e.getDescription(), e.isVisibleToClient(), e.getUploadedBy().getFullName(), e.getDate());
    }

    /**
     * Archivo stored.
     * @param resource recurso (Resource)
     * @param contentType tipo de content (String)
     * @param fileName nombre de archivo (String)
     * @return record
     */
    public record StoredFile(Resource resource, String contentType, String fileName) {
    }
}
