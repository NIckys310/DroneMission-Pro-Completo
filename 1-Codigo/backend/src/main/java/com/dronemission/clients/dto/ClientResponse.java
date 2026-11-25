// ============================================================================================
// DOCUMENTACIÓN · clients/dto/ClientResponse.java
//
// QUÉ ES:        Los datos que el backend DEVUELVE (ClientResponse) sobre los
//                clientes.
// QUÉ HACE:      Es un "record": solo lleva lo que la pantalla necesita, sin datos
//                sensibles.
// SI LO CAMBIAS: Si cambias un campo, cambia la interface igual en
//                frontend/src/types/index.ts o la pantalla dejará de mostrarlo.
// ============================================================================================
package com.dronemission.clients.dto;

public record ClientResponse(Long id, String type, String name, String document, String email, String phone,
                             String address, String city, boolean active, long missionCount, String userEmail) {
}
