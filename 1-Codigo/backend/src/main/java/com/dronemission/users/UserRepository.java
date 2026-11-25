// ============================================================================================
// DOCUMENTACIÓN · users/UserRepository.java
//
// QUÉ ES:        El acceso a la base de datos para los usuarios, roles y permisos.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.users;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface UserRepository extends MongoRepository<User, Long> {

    /**
     * Busca por correo (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param email correo (String)
     * @return opcional: User
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Busca por nombre de usuario (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param username nombre de usuario (String)
     * @return opcional: User
     */
    Optional<User> findByUsernameIgnoreCase(String username);

    /**
     * Busca por token de recuperación de contraseña. Spring Data arma la consulta a partir del nombre del método.
     * @param resetToken token de recuperación de contraseña (String)
     * @return opcional: User
     */
    Optional<User> findByResetToken(String resetToken);

    /**
     * Indica si existe por nombre de usuario (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param username nombre de usuario (String)
     * @return boolean
     */
    boolean existsByUsernameIgnoreCase(String username);

    /**
     * Busca por ending de correo con (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param suffix suffix (String)
     * @return lista de User
     */
    List<User> findByEmailEndingWithIgnoreCase(String suffix);

    /**
     * Indica si existe por correo (sin distinguir mayúsculas). Spring Data arma la consulta a partir del nombre del método.
     * @param email correo (String)
     * @return boolean
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Busca todos. Ordena por identificador numérico (lo asigna SequenceGeneratorService) (ascendente). Spring Data arma la consulta a partir del nombre del método.
     * @return lista de User
     */
    List<User> findAllByOrderByIdAsc();

    /**
     * Busca por verdadero activo. Spring Data arma la consulta a partir del nombre del método.
     * @return lista de User
     */
    List<User> findByActiveTrue();

    // El rol es otro documento (DBRef): se filtra en Java en lugar de "role.name"
    default List<User> findByRoleNameAndActiveTrue(RoleName roleName) {
        return findByActiveTrue().stream().filter(u -> u.getRole().getName() == roleName).toList();
    }
}
