// ============================================================================================
// DOCUMENTACIÓN · auth/dto/ResetPasswordRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (ResetPasswordRequest) para el inicio
//                de sesión.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "El token es obligatorio") String token,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres") String newPassword
) {
}
