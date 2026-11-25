// ============================================================================================
// DOCUMENTACIÓN · auth/dto/ForgotPasswordResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (ForgotPasswordResponse) sobre el
//                inicio de sesión.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.auth.dto;

/**
 * Respuesta de la recuperación de contraseña SIMULADA.
 * En un sistema real el token se enviaría por correo; aquí se devuelve para la demo.
 */
public record ForgotPasswordResponse(String message, String simulatedToken) {
}
