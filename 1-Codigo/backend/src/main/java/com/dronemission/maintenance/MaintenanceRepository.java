// ============================================================================================
// DOCUMENTACIÓN · maintenance/MaintenanceRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los mantenimientos.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.maintenance;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface MaintenanceRepository extends MongoRepository<Maintenance, Long> {

    /**
     * Busca todos. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Maintenance
     */
    List<Maintenance> findAllByOrderByDateDesc();

    /**
     * Busca por identificador de drone. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param droneId identificador de drone (Long)
     * @return lista de Maintenance
     */
    List<Maintenance> findByDroneIdOrderByDateDesc(Long droneId);

    /**
     * Busca por estado en. Ordena por fecha (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @param statuses statuses (colección de MaintenanceStatus)
     * @return lista de Maintenance
     */
    List<Maintenance> findByStatusInOrderByDateAsc(Collection<MaintenanceStatus> statuses);

    /**
     * Indica si existe por identificador de drone y estado y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param droneId identificador de drone (Long)
     * @param status estado (MaintenanceStatus)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByDroneIdAndStatusAndIdNot(Long droneId, MaintenanceStatus status, Long id);

    /**
     * Indica si existe por identificador de drone y estado. Spring Data arma la consulta a partir del nombre del método.
     * @param droneId identificador de drone (Long)
     * @param status estado (MaintenanceStatus)
     * @return boolean
     */
    boolean existsByDroneIdAndStatus(Long droneId, MaintenanceStatus status);
}
