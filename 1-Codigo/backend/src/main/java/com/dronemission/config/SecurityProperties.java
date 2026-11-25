// ============================================================================================
// DOCUMENTACIÓN · config/SecurityProperties.java
//
// QUÉ ES:        Las opciones de seguridad que vienen de application.yml.
// QUÉ HACE:      Agrupa en un solo objeto: usuarios demo, registro, intentos máximos,
//                minutos de bloqueo, límite por minuto y datos del admin.
// SI LO CAMBIAS: Opción nueva: agrégala aquí y en application.yml (sección
//                app.security).
// ============================================================================================
package com.dronemission.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Opciones de seguridad configurables por variables de entorno (ver application.yml).
 * En la nube: usuarios demo desactivados, token de recuperación oculto y
 * administrador creado a partir de variables secretas.
 */
@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(
        boolean demoUsers,
        boolean registrationEnabled,
        boolean showResetToken,
        int maxFailedAttempts,
        int lockMinutes,
        int authRequestsPerMinute,
        Admin admin
) {
    /**
     * Administrador.
     * @param email correo (String)
     * @param username nombre de usuario (String)
     * @param firstName nombre (String)
     * @param lastName apellidos (String)
     * @param password contraseña (String)
     * @param resetPassword contraseña de recuperación (boolean)
     * @return record
     */
    public record Admin(String email, String username, String firstName, String lastName, String password,
                        boolean resetPassword) {
        public boolean isConfigured() {
            return email != null && !email.isBlank() && password != null && !password.isBlank();
        }
    }
}
