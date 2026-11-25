// ============================================================================================
// DOCUMENTACIÓN · auth/dto/LoginResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (LoginResponse) sobre el inicio de
//                sesión.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.auth.dto;

public record LoginResponse(String token, String tokenType, long expiresInMinutes, UserInfo user) {
}
