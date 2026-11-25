// ============================================================================================
// DOCUMENTACIÓN · missions/MissionRepository.java
//
// QUÉ ES:        Las consultas a la base sobre misiones.
// QUÉ HACE:      Spring Data arma la consulta a partir del nombre del método
//                (findByClientId...).
// SI LO CAMBIAS: Consulta nueva: basta con declarar un método con el nombre adecuado.
// ============================================================================================
package com.dronemission.missions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface MissionRepository extends MongoRepository<Mission, Long> {

    /**
     * Busca todos. Ordena por fecha programada (descendente), hora de inicio (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Mission
     */
    List<Mission> findAllByOrderByScheduledDateDescStartTimeDesc();

    /**
     * Busca por estado. Spring Data arma la consulta a partir del nombre del método.
     * @param status estado (MissionStatus)
     * @return lista de Mission
     */
    List<Mission> findByStatus(MissionStatus status);

    /**
     * Cuenta por estado. Spring Data arma la consulta a partir del nombre del método.
     * @param status estado (MissionStatus)
     * @return long
     */
    long countByStatus(MissionStatus status);

    /**
     * Cuenta por identificador de cliente. Spring Data arma la consulta a partir del nombre del método.
     * @param clientId identificador de cliente (Long)
     * @return long
     */
    long countByClientId(Long clientId);

    /**
     * Indica si existe por identificador de cliente y estado en. Spring Data arma la consulta a partir del nombre del método.
     * @param clientId identificador de cliente (Long)
     * @param statuses statuses (colección de MissionStatus)
     * @return boolean
     */
    boolean existsByClientIdAndStatusIn(Long clientId, Collection<MissionStatus> statuses);

    /**
     * Busca por identificador de cliente. Ordena por fecha programada (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param clientId identificador de cliente (Long)
     * @return lista de Mission
     */
    List<Mission> findByClientIdOrderByScheduledDateDesc(Long clientId);

    // Rango inclusivo (como BETWEEN de SQL). "fecha_programada" es el nombre del campo en MongoDB
    @Query("{ 'fecha_programada': { $gte: ?0, $lte: ?1 } }")
    List<Mission> findByScheduledDateBetween(LocalDate from, LocalDate to);

    /**
     * Busca todos. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @return opcional: Mission
     */
    Optional<Mission> findTopByOrderByIdDesc();

    // Misiones sin plan de vuelo: el arreglo embebido "plan_vuelo" no existe o está vacío
    @Query("{ $or: [ { 'plan_vuelo': { $exists: false } }, { 'plan_vuelo': { $size: 0 } } ] }")
    List<Mission> findWithoutFlightPlan();
}
