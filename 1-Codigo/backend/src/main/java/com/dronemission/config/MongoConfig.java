// ============================================================================================
// DOCUMENTACIÓN · config/MongoConfig.java
//
// QUÉ ES:        Configuración de MongoDB.
// QUÉ HACE:      Registra el administrador de transacciones para que @Transactional
//                funcione con MongoDB.
// SI LO CAMBIAS: La conexión (URL, base) está en application.yml, no aquí.
// ============================================================================================
package com.dronemission.config;

import com.dronemission.common.persistence.MongoUnitOfWorkTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuración de MongoDB.
 *
 * La conexión (embebida o MONGODB_URI) la arma Spring Boot con application.yml.
 * Aquí solo se registra el administrador de transacciones, para que los @Transactional
 * de los servicios sigan funcionando igual que con JPA.
 */
@Configuration
public class MongoConfig {

    /**
     * Administrador de transacción.
     * @param mongoTemplate plantilla de MongoDB (MongoTemplate)
     * @return PlatformTransactionManager
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(MongoTemplate mongoTemplate) {
        return new MongoUnitOfWorkTransactionManager(mongoTemplate);
    }
}
