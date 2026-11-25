// ============================================================================================
// DOCUMENTACIÓN · auth/dto/ForgotPasswordRequest.java
//
// QUÉ ES:        Los datos que el frontend ENVÍA (ForgotPasswordRequest) para el
//                inicio de sesión.
// QUÉ HACE:      Es un "record" (una cajita de datos). Las anotaciones @NotNull,
//                @Size, etc. validan antes de llegar al servicio; si algo falla,
//                responde 400 con el error por campo.
// SI LO CAMBIAS: Si agregas o quitas un campo, cambia también la interface del
//                frontend (types/index.ts) y su formulario.
// ============================================================================================
package com.dronemission.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(@NotBlank(message = "El correo es obligatorio") @Email(message = "El correo no es válido") String email) {
}
