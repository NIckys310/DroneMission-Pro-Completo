// ============================================================================================
// DOCUMENTACIÓN · pilots/PilotRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los pilotos.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.pilots;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface PilotRepository extends MongoRepository<Pilot, Long> {

    /**
     * Busca todos. Ordena por nombre (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Pilot
     */
    List<Pilot> findAllByOrderByFirstNameAsc();

    /**
     * Busca por identificador de usuario. Spring Data arma la consulta a partir del nombre del método.
     * @param userId identificador de usuario (Long)
     * @return opcional: Pilot
     */
    Optional<Pilot> findByUserId(Long userId);

    /**
     * Busca por verdadero activo. Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Pilot
     */
    List<Pilot> findByActiveTrue();

    /**
     * Cuenta por verdadero activo. Spring Data arma la consulta a partir del nombre del método.
     * @return long
     */
    long countByActiveTrue();

    /**
     * Cuenta por verdadero activo y estado. Spring Data arma la consulta a partir del nombre del método.
     * @param status estado (PilotStatus)
     * @return long
     */
    long countByActiveTrueAndStatus(PilotStatus status);

    // Rango inclusivo de fechas de vencimiento ("Between" de Mongo excluiría los extremos)
    @Query("{ 'activo': true, 'vencimiento_licencia': { $gte: ?0, $lte: ?1 } }")
    List<Pilot> findByActiveTrueAndLicenseExpiresOnBetween(LocalDate from, LocalDate to);

    /**
     * Indica si existe por documento. Spring Data arma la consulta a partir del nombre del método.
     * @param document documento (String)
     * @return boolean
     */
    boolean existsByDocument(String document);

    /**
     * Indica si existe por correo (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param email correo (String)
     * @return boolean
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Indica si existe por número de licencia de piloto. Spring Data arma la consulta a partir del nombre del método.
     * @param licenseNumber número de licencia de piloto (String)
     * @return boolean
     */
    boolean existsByLicenseNumber(String licenseNumber);

    /**
     * Indica si existe por documento y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param document documento (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByDocumentAndIdNot(String document, Long id);

    /**
     * Indica si existe por correo y no de identificador (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param email correo (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    /**
     * Indica si existe por número de licencia de piloto y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param licenseNumber número de licencia de piloto (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByLicenseNumberAndIdNot(String licenseNumber, Long id);
}
