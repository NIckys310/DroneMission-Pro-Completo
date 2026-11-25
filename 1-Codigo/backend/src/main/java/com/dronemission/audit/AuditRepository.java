// ============================================================================================
// DOCUMENTACIÓN · audit/AuditRepository.java
//
// QUÉ ES:        El acceso a la base de datos para la auditoría (bitácora de quién
//                hizo qué).
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.audit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface AuditRepository extends MongoRepository<AuditLog, Long> {

    /**
     * Busca todos. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param pageable pageable (Pageable)
     * @return lista de AuditLog
     */
    List<AuditLog> findAllByOrderByDateDesc(Pageable pageable);

    /**
     * Busca por entidad y id de la entidad afectada. Ordena por fecha (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @param entity entidad (String)
     * @param entityId id de la entidad afectada (Long)
     * @return lista de AuditLog
     */
    List<AuditLog> findByEntityAndEntityIdOrderByDateAsc(String entity, Long entityId);
}
