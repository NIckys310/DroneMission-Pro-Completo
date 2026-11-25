// ============================================================================================
// DOCUMENTACIÓN · common/persistence/BeforeUpdate.java
//
// QUÉ ES:        Una etiqueta (anotación) para métodos de las entidades.
// QUÉ HACE:      El método marcado se ejecuta antes de ACTUALIZAR un documento (por
//                ejemplo, la fecha de modificación).
// SI LO CAMBIAS: Si la quitas, la fecha de modificación deja de actualizarse. La lee
//                MongoLifecycleListener.
// ============================================================================================
package com.dronemission.common.persistence;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marca un método de una entidad que debe ejecutarse justo antes de ACTUALIZAR
 * un documento que ya existe en MongoDB (por ejemplo, la fecha de modificación).
 * Reemplaza a @PreUpdate de JPA. Lo ejecuta {@link MongoLifecycleListener}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BeforeUpdate {
}
