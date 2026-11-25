// ============================================================================================
// DOCUMENTACIÓN · clients/ClientRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los clientes.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.clients;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface ClientRepository extends MongoRepository<Client, Long> {

    /**
     * Busca todos. Ordena por nombre (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de Client
     */
    List<Client> findAllByOrderByNameAsc();

    /**
     * Busca por identificador de usuario. Spring Data arma la consulta a partir del nombre del método.
     * @param userId identificador de usuario (Long)
     * @return opcional: Client
     */
    Optional<Client> findByUserId(Long userId);

    /**
     * Indica si existe por documento. Spring Data arma la consulta a partir del nombre del método.
     * @param document documento (String)
     * @return boolean
     */
    boolean existsByDocument(String document);

    /**
     * Indica si existe por documento y no de identificador. Spring Data arma la consulta a partir del nombre del método.
     * @param document documento (String)
     * @param id identificador numérico (lo asigna SequenceGeneratorService) (Long)
     * @return boolean
     */
    boolean existsByDocumentAndIdNot(String document, Long id);
}
