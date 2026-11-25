// ============================================================================================
// DOCUMENTACIÓN · common/persistence/BeforeCreate.java
//
// QUÉ ES:        Una etiqueta (anotación) para métodos de las entidades.
// QUÉ HACE:      El método marcado se ejecuta justo antes de guardar un documento
//                NUEVO (por ejemplo, poner la fecha de creación).
// SI LO CAMBIAS: Si la quitas de un método, ese método deja de ejecutarse solo. La lee
//                MongoLifecycleListener.
// ============================================================================================
package com.dronemission.common.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca un método de una entidad que debe ejecutarse justo antes de guardar
 * un documento NUEVO en MongoDB (por ejemplo, para llenar la fecha de creación).
 * Reemplaza a @PrePersist de JPA. Lo ejecuta {@link MongoLifecycleListener}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeCreate {
}
