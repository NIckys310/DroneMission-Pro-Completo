// ============================================================================================
// DOCUMENTACIÓN · auth/AuthController.java
//
// QUÉ ES:        La puerta HTTP del login (/api/auth): login, registro, perfil, logout
//                y recuperar contraseña.
// QUÉ HACE:      No tiene lógica: recibe los datos y llama a AuthService.
// SI LO CAMBIAS: Si cambias una ruta, cambia authApi en el frontend (api/endpoints.ts)
//                y la lista pública de SecurityConfig.
// ============================================================================================
package com.dronemission.auth;

import com.dronemission.auth.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación")
public class AuthController {

    /** Dependencia inyectada por Spring (por constructor): servicio de auth. */
    private final AuthService authService;

    /**
     * Inicia sesión auth.
     * Endpoint POST /api/auth/login (lo llama el frontend desde api/endpoints.ts).
     * @param request petición (LoginRequest)
     * @return LoginResponse
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener un token JWT")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Registra auth.
     * Endpoint POST /api/auth/register (lo llama el frontend desde api/endpoints.ts).
     * @param request petición (RegisterRequest)
     * @return MessageResponse
     */
    @PostMapping("/register")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    @Operation(summary = "Crear una cuenta (queda pendiente de aprobación del administrador)")
    public MessageResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * Configuración pública.
     * Endpoint GET /api/auth/config (lo llama el frontend desde api/endpoints.ts).
     * @return AuthConfigResponse
     */
    @GetMapping("/config")
    @Operation(summary = "Opciones públicas del login (registro habilitado, usuarios demo, política de contraseñas)")
    public AuthConfigResponse config() {
        return authService.config();
    }

    /**
     * Datos del usuario actual.
     * Endpoint GET /api/auth/me (lo llama el frontend desde api/endpoints.ts).
     * @return UserInfo
     */
    @GetMapping("/me")
    @Operation(summary = "Datos del usuario autenticado")
    public UserInfo me() {
        return authService.me();
    }

    /**
     * Cierra sesión auth.
     * Endpoint POST /api/auth/logout (lo llama el frontend desde api/endpoints.ts).
     * @return ResponseEntity<Void>
     */
    @PostMapping("/logout")
    @Operation(summary = "Registrar el cierre de sesión (el frontend descarta el token)")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    /**
     * Solicita recuperar contraseña.
     * Endpoint POST /api/auth/forgot-password (lo llama el frontend desde api/endpoints.ts).
     * @param request petición (ForgotPasswordRequest)
     * @return ForgotPasswordResponse
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar recuperación de contraseña (simulada)")
    public ForgotPasswordResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    /**
     * Restablece contraseña.
     * Endpoint POST /api/auth/reset-password (lo llama el frontend desde api/endpoints.ts).
     * @param request petición (ResetPasswordRequest)
     * @return MessageResponse
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Restablecer la contraseña con el token de recuperación")
    public MessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return authService.resetPassword(request);
    }
}
