// ============================================================================================
// DOCUMENTACIÓN · reports/ReportRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los reportes PDF.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.reports;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface ReportRepository extends MongoRepository<Report, Long> {

    /**
     * Busca todos. Ordena por fecha y hora de generación (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Report
     */
    List<Report> findAllByOrderByGeneratedAtDesc();

    // MongoDB no cruza report -> misión -> cliente en una sola consulta: se filtra en Java
    default List<Report> findByMissionClientIdOrderByGeneratedAtDesc(Long clientId) {
        return findAllByOrderByGeneratedAtDesc().stream()
                .filter(r -> r.getMission() != null && clientId.equals(r.getMission().getClient().getId()))
                .toList();
    }
}
