// ============================================================================================
// DOCUMENTACIÓN · telemetry/TelemetryRepository.java
//
// QUÉ ES:        El acceso a la base de datos para la telemetría (datos del vuelo).
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.telemetry;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface TelemetryRepository extends MongoRepository<Telemetry, Long> {

    /**
     * Busca por identificador de misión. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @return lista de Telemetry
     */
    List<Telemetry> findByMissionIdOrderByIdAsc(Long missionId);

    /**
     * Busca por identificador de misión y que de mayor de identificador. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @param afterId identificador de after (Long)
     * @return lista de Telemetry
     */
    List<Telemetry> findByMissionIdAndIdGreaterThanOrderByIdAsc(Long missionId, Long afterId);

    /**
     * Busca por identificador de misión. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @return opcional: Telemetry
     */
    Optional<Telemetry> findTopByMissionIdOrderByIdDesc(Long missionId);

    /**
     * Busca por identificador de misión. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @param missionId identificador de misión (Long)
     * @return opcional: Telemetry
     */
    Optional<Telemetry> findTopByMissionIdOrderByIdAsc(Long missionId);
}
