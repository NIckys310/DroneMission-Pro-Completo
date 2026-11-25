// ============================================================================================
// DOCUMENTACIÓN · drones/DroneRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los drones.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.drones;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface DroneRepository extends MongoRepository<Drone, Long> {

    /**
     * Busca todos. Ordena por código legible (por ejemplo, DRN-001 o MSN-0001) (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Drone
     */
    List<Drone> findAllByOrderByCodeAsc();

    /**
     * Busca por verdadero activo. Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Drone
     */
    List<Drone> findByActiveTrue();

    /**
     * Cuenta por verdadero activo. Spring Data arma la consulta a partir del nombre del método.
     * @return long
     */
    long countByActiveTrue();

    /**
     * Cuenta por verdadero activo y estado. Spring Data arma la consulta a partir del nombre del método.
     * @param status estado (DroneStatus)
     * @return long
     */
    long countByActiveTrueAndStatus(DroneStatus status);

    /**
     * Busca por verdadero activo y mantenimiento próximo el equal de que de less. Spring Data arma la consulta a partir del nombre del método.
     * @param date fecha (LocalDate)
     * @return lista de Drone
     */
    List<Drone> findByActiveTrueAndNextMaintenanceOnLessThanEqual(LocalDate date);

    /**
     * Indica si existe por código legible (por ejemplo, DRN-001 o MSN-0001). Spring Data arma la consulta a partir del nombre del método.
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @return boolean
     */
    boolean existsByCode(String code);

    /**
     * Indica si existe por número de serie (único). Spring Data arma la consulta a partir del nombre del método.
     * @param serialNumber número de serie (único) (String)
     * @return boolean
     */
    boolean existsBySerialNumber(String serialNumber);

    /**
     * Indica si existe por código legible (por ejemplo, DRN-001 o MSN-0001) y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param code código legible (por ejemplo, DRN-001 o MSN-0001) (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByCodeAndIdNot(String code, Long id);

    /**
     * Indica si existe por número de serie (único) y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param serialNumber número de serie (único) (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsBySerialNumberAndIdNot(String serialNumber, Long id);
}
