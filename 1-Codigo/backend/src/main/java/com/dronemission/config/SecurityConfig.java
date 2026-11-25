// ============================================================================================
// DOCUMENTACIÓN · config/SecurityConfig.java
//
// QUÉ ES:        Las reglas de seguridad de todo el backend.
// QUÉ HACE:      Dice qué rutas son públicas (login, Swagger, frontend) y cuáles piden
//                token, activa el filtro JWT, CORS y el cifrado de contraseñas BCrypt.
// SI LO CAMBIAS: Ruta pública nueva: agrégala en la lista permitAll. Si quitas una
//                ruta de la lista, pedirá token.
// ============================================================================================
package com.dronemission.config;

import com.dronemission.auth.JwtAuthenticationFilter;
import com.dronemission.common.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad:
 *  - API sin sesión (stateless) autenticada con JWT.
 *  - Rutas públicas: login, recuperación de contraseña, Swagger, archivos demo y el frontend.
 *  - La autorización fina se hace con @PreAuthorize en cada controller.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /** Dependencia inyectada por Spring (por constructor): filtro de autenticación de JWT. */
    private final JwtAuthenticationFilter jwtFilter;
    /** Dependencia inyectada por Spring (por constructor): filtro de limit de tasa de auth. */
    private final AuthRateLimitFilter rateLimitFilter;
    /** Dependencia inyectada por Spring (por constructor): conversor de objeto. */
    private final ObjectMapper objectMapper;

    /** Atributo allowedOrigins (String): orígenes permitidas. */
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * Cadena de filtro de seguridad.
     * @param http http (HttpSecurity)
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(h -> h.frameOptions(f -> f.sameOrigin())) // permite mostrar páginas propias dentro de un iframe
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/auth/config",
                                "/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/demo/**", "/error").permitAll()
                        // Toda la API exige token; el resto son archivos del frontend (cuando se sirve desde el backend)
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) ->
                                writeError(res, 401, "Unauthorized", "Debes iniciar sesión para acceder a este recurso", req.getRequestURI()))
                        .accessDeniedHandler((req, res, e) ->
                                writeError(res, 403, "Forbidden", "No tienes permiso para realizar esta operación", req.getRequestURI())))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitFilter, JwtAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Cifrador de contraseña.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // >> El 12 es la "fuerza" del cifrado de contraseñas. Más alto = más seguro pero el login es más lento. Las contraseñas ya guardadas siguen sirviendo.
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Origen de configuration de cors.
     * @return CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).map(String::trim).toList());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Error de write.
     * @param res res (HttpServletResponse)
     * @param status estado (int)
     * @param error error (String)
     * @param message mensaje (String)
     * @param path ruta (String)
     */
    private void writeError(HttpServletResponse res, int status, String error, String message, String path) throws IOException {
        res.setStatus(status);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(res.getOutputStream(), ApiError.of(status, error, message, path));
    }
}
