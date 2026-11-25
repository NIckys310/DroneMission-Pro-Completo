// ============================================================================================
// DOCUMENTACIÓN · config/OpenApiConfig.java
//
// QUÉ ES:        Configuración de Swagger (la documentación interactiva de la API).
// QUÉ HACE:      Arma la página http://localhost:8080/swagger-ui.html con el botón
//                "Authorize" para pegar el token JWT.
// SI LO CAMBIAS: Solo cambia textos de la documentación; no afecta al sistema.
// ============================================================================================
package com.dronemission.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configura Swagger UI (http://localhost:8080/swagger-ui.html) con autenticación JWT. */
@Configuration
public class OpenApiConfig {

    /**
     * Api abierto de misión de drone.
     * @return OpenAPI
     */
    @Bean
    public OpenAPI droneMissionOpenApi() {
        String scheme = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("DroneMission Pro API")
                        .version("1.0.0")
                        .description("API REST para la gestión de misiones de drones. "
                                + "Inicia sesión en POST /api/auth/login y usa el token en el botón 'Authorize'."))
                .addSecurityItem(new SecurityRequirement().addList(scheme))
                .components(new Components().addSecuritySchemes(scheme,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
