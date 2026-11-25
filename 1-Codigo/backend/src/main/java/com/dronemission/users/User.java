// ============================================================================================
// DOCUMENTACIÓN · users/User.java
//
// QUÉ ES:        El molde de un usuario que inicia sesión (colección "usuarios").
// QUÉ HACE:      Guarda nombre, usuario, correo, el HASH de la contraseña (nunca la
//                contraseña), su rol, si está activo, los intentos fallidos y el
//                bloqueo. Puede estar ligado a un piloto o a un cliente.
// SI LO CAMBIAS: Campo nuevo: aquí + UserRequest/UserResponse + UserService + frontend
//                (types y UsersPage.tsx).
// ============================================================================================
package com.dronemission.users;

import com.dronemission.common.persistence.BeforeCreate;
import com.dronemission.common.persistence.BeforeUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Usuario que inicia sesión en el sistema. Tiene exactamente un rol. */
// MongoDB: cada objeto se guarda como un documento en la colección "usuarios"
@Document(collection = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class User {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    /** Atributo firstName (String): nombre. */
    @Field("nombre")
    private String firstName;

    /** Atributo lastName (String): apellidos. */
    @Field("apellidos")
    private String lastName;

    /** Atributo email (String): correo. Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("correo")
    private String email;

    /** Nombre de usuario para iniciar sesión (alternativa al correo). */
    @Indexed(unique = true)
    @Field("nombre_usuario")
    private String username;

    // Nunca se guarda la contraseña: solo su hash BCrypt
    @Field("contrasena_hash")
    private String passwordHash;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("rol_id")
    private Role role;

    /** Atributo active (boolean): true si está activo (baja lógica). */
    @Field("activo")
    private boolean active = true;

    /** Cuenta creada por registro público que espera la aprobación de un administrador. */
    @Field("pendiente_aprobacion")
    private boolean pendingApproval = false;

    /** Intentos de login fallidos consecutivos (protección contra fuerza bruta). */
    @Field("intentos_fallidos")
    private int failedAttempts = 0;

    /** Atributo lockedUntil (LocalDateTime): hasta cuándo está bloqueada la cuenta (null = no bloqueada). */
    @Field("bloqueado_hasta")
    private LocalDateTime lockedUntil;

    /** Atributo resetToken (String): token de recuperación de contraseña. */
    @Field("token_recuperacion")
    private String resetToken;

    /** Atributo resetTokenExpiresAt (LocalDateTime): vencimiento del token de recuperación. */
    @Field("token_expira_en")
    private LocalDateTime resetTokenExpiresAt;

    /** Atributo lastLoginAt (LocalDateTime): último inicio de sesión. */
    @Field("ultimo_acceso")
    private LocalDateTime lastLoginAt;

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt;

    /** Atributo updatedAt (LocalDateTime): fecha y hora de la última actualización. */
    @Field("actualizado_en")
    private LocalDateTime updatedAt;

    // Se ejecuta antes de guardar un documento nuevo (lo llama MongoLifecycleListener)
    @BeforeCreate
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    // Se ejecuta antes de actualizar un documento existente (lo llama MongoLifecycleListener)
    @BeforeUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Obtiene nombre completo.
     * @return String
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Indica si es bloqueado.
     * @param now ahora (LocalDateTime)
     * @return boolean
     */
    public boolean isLocked(LocalDateTime now) {
        return lockedUntil != null && lockedUntil.isAfter(now);
    }

    /**
     * Indica si tiene rol.
     * @param roleName nombre de rol (RoleName)
     * @return boolean
     */
    public boolean hasRole(RoleName roleName) {
        return role != null && role.getName() == roleName;
    }
}
