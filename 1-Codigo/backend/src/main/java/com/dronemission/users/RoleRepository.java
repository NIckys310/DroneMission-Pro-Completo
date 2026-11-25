// ============================================================================================
// DOCUMENTACIÓN · users/RoleRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los usuarios, roles y permisos.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.users;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface RoleRepository extends MongoRepository<Role, Long> {

    /**
     * Busca por nombre. Spring Data arma la consulta a partir del nombre del método.
     * @param name nombre (RoleName)
     * @return opcional: Role
     */
    Optional<Role> findByName(RoleName name);
}
