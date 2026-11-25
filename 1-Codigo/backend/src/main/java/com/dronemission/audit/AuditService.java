// ============================================================================================
// DOCUMENTACIÓN · audit/AuditService.java
//
// QUÉ ES:        La lógica de la auditoría.
// QUÉ HACE:      Guarda un registro nuevo y consulta los últimos registros (los usa la
//                pantalla Auditoría).
// SI LO CAMBIAS: Si cambias el usuario "sistema", cambia cómo aparecen las acciones
//                automáticas en la bitácora.
// ============================================================================================
package com.dronemission.audit;

import com.dronemission.audit.dto.AuditResponse;
import com.dronemission.auth.AuthenticatedUser;
import com.dronemission.auth.CurrentUserService;
import com.dronemission.users.User;
import com.dronemission.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Registra y consulta la bitácora de auditoría (regla de negocio 11). */
@Service
@RequiredArgsConstructor
public class AuditService {

    /** Constante SYSTEM_USER: usuario de system. */
    public static final String SYSTEM_USER = "sistema";

    /** Dependencia inyectada por Spring (por constructor): repositorio de auditoría. */
    private final AuditRepository auditRepository;
    /** Dependencia inyectada por Spring (por constructor): repositorio de usuario. */
    private final UserRepository userRepository;
    /** Dependencia inyectada por Spring (por constructor): servicio de usuario actual. */
    private final CurrentUserService currentUserService;

    /** Registra una acción hecha por el usuario de la petición actual (o por el sistema). */
    @Transactional
    public void log(String action, String entity, Long entityId, String description) {
        Optional<AuthenticatedUser> current = currentUserService.find();
        User user = current.flatMap(u -> userRepository.findById(u.id())).orElse(null);
        save(user, current.map(AuthenticatedUser::email).orElse(SYSTEM_USER), action, entity, entityId, description);
    }

    /** Registra una acción indicando explícitamente el usuario (por ejemplo, en el login). */
    @Transactional
    public void logAs(User user, String email, String action, String entity, Long entityId, String description) {
        save(user, email, action, entity, entityId, description);
    }

    /**
     * Busca recientes.
     * Transaccional: si algo falla, no se guarda nada.
     * @param limit limit (int)
     * @return lista de AuditResponse
     */
    @Transactional(readOnly = true)
    public List<AuditResponse> findRecent(int limit) {
        return auditRepository.findAllByOrderByDateDesc(PageRequest.of(0, limit)).stream().map(this::toResponse).toList();
    }

    /**
     * Busca auditoría por entidad.
     * Transaccional: si algo falla, no se guarda nada.
     * @param entity entidad (String)
     * @param entityId id de la entidad afectada (Long)
     * @return lista de AuditResponse
     */
    @Transactional(readOnly = true)
    public List<AuditResponse> findByEntity(String entity, Long entityId) {
        return auditRepository.findByEntityAndEntityIdOrderByDateAsc(entity, entityId).stream().map(this::toResponse).toList();
    }

    /**
     * Guarda auditoría.
     * @param user usuario (User)
     * @param email correo (String)
     * @param action acción (String)
     * @param entity entidad (String)
     * @param entityId id de la entidad afectada (Long)
     * @param description descripción (String)
     */
    private void save(User user, String email, String action, String entity, Long entityId, String description) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setUserEmail(email);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDescription(description);
        auditRepository.save(log);
    }

    /**
     * Convierte a respuesta.
     * @param log log (AuditLog)
     * @return AuditResponse
     */
    private AuditResponse toResponse(AuditLog log) {
        return new AuditResponse(log.getId(), log.getUserEmail(), log.getAction(), log.getEntity(),
                log.getEntityId(), log.getDescription(), log.getDate());
    }
}
