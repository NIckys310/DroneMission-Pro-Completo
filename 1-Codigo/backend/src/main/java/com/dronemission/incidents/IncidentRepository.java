// ============================================================================================
// DOCUMENTACIÓN · incidents/IncidentRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los incidentes.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.incidents;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface IncidentRepository extends MongoRepository<Incident, Long> {

    /**
     * Busca todos. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Incident
     */
    List<Incident> findAllByOrderByDateDesc();

    /**
     * Busca todos. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param pageable pageable (Pageable)
     * @return lista de Incident
     */
    List<Incident> findAllByOrderByDateDesc(Pageable pageable);

    /**
     * Busca por identificador de misión. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @return lista de Incident
     */
    List<Incident> findByMissionIdOrderByDateDesc(Long missionId);

    /**
     * Indica si existe por identificador de misión y tipo. Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @param type tipo (IncidentType)
     * @return boolean
     */
    boolean existsByMissionIdAndType(Long missionId, IncidentType type);

    /**
     * Cuenta por no de estado. Spring Data arma la consulta a partir del nombre del método.
     * @param status estado (IncidentStatus)
     * @return long
     */
    long countByStatusNot(IncidentStatus status);

    /**
     * Cuenta por gravedad y no de estado. Spring Data arma la consulta a partir del nombre del método.
     * @param severity gravedad (IncidentSeverity)
     * @param status estado (IncidentStatus)
     * @return long
     */
    long countBySeverityAndStatusNot(IncidentSeverity severity, IncidentStatus status);
}
