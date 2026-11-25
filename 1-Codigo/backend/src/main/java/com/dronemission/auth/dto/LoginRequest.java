// ============================================================================================
// DOCUMENTACIÓN · auth/dto/LoginRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (LoginRequest) para el inicio de
//                sesión.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Se puede iniciar sesión con el correo o con el nombre de usuario. */
public record LoginRequest(
        @JsonAlias("email")
        @NotBlank(message = "El correo o usuario es obligatorio") @Size(max = 120) String identifier,
        @NotBlank(message = "La contraseña es obligatoria") @Size(max = 64) String password
) {
}
