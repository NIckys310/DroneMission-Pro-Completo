// ============================================================================================
// DOCUMENTACIÓN · auth/dto/MessageResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (MessageResponse) sobre el inicio
//                de sesión.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.auth.dto;

public record MessageResponse(String message) {
}
