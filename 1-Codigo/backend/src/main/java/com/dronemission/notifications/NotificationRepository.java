// ============================================================================================
// DOCUMENTACIÓN · notifications/NotificationRepository.java
//
// QUÉ ES:        El acceso a la base de datos para las notificaciones.
// QUÉ HACE:      Es una interfaz: Spring Data MongoDB escribe solo las consultas a
//                partir del nombre de cada método (findBy..., existsBy...).
// SI LO CAMBIAS: Consulta nueva: declara un método con el nombre adecuado. Si cambias
//                uno, el compilador marca dónde se usa.
// ============================================================================================
package com.dronemission.notifications;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// MongoRepository: Spring Data genera las consultas a MongoDB a partir del nombre del método
public interface NotificationRepository extends MongoRepository<Notification, Long> {

    /**
     * Busca por identificador de usuario. Ordena por fecha (descendente). Spring Data arma la consulta a partir del nombre del método.
     * @param userId identificador de usuario (Long)
     * @param pageable pageable (Pageable)
     * @return lista de Notification
     */
    List<Notification> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    /**
     * Busca por identificador de usuario y falso de leída. Spring Data arma la consulta a partir del nombre del método.
     * @param userId identificador de usuario (Long)
     * @return lista de Notification
     */
    List<Notification> findByUserIdAndReadFalse(Long userId);

    /**
     * Cuenta por identificador de usuario y falso de leída. Spring Data arma la consulta a partir del nombre del método.
     * @param userId identificador de usuario (Long)
     * @return long
     */
    long countByUserIdAndReadFalse(Long userId);
}
