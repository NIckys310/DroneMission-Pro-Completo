// ============================================================================================
// DOCUMENTACIÓN · clients/ClientMapper.java
//
// QUÉ ES:        El traductor entre el cliente guardado y lo que viaja por la API.
// QUÉ HACE:      Pasa los datos de ClientRequest a Client y de Client a
//                ClientResponse.
// SI LO CAMBIAS: Si agregas un campo y olvidas ponerlo aquí, se guarda vacío o no
//                llega al frontend.
// ============================================================================================
package com.dronemission.clients;

import com.dronemission.clients.dto.ClientRequest;
import com.dronemission.clients.dto.ClientResponse;

/** Convierte entre la entidad Client y sus DTOs. */
public final class ClientMapper {

    /**
     * Constructor de ClientMapper.
     */
    private ClientMapper() {
    }

    /**
     * Aplica cliente.
     * @param req req (ClientRequest)
     * @param client cliente (Client)
     */
    public static void apply(ClientRequest req, Client client) {
        client.setType(req.type());
        client.setName(req.name().trim());
        client.setDocument(req.document().trim());
        client.setEmail(req.email().trim().toLowerCase());
        client.setPhone(req.phone().trim());
        client.setAddress(req.address().trim());
        client.setCity(req.city().trim());
        if (req.active() != null) {
            client.setActive(req.active());
        }
    }

    /**
     * Convierte a respuesta.
     * @param c (°C) (Client)
     * @param missionCount cantidad de misión (long)
     * @return ClientResponse
     */
    public static ClientResponse toResponse(Client c, long missionCount) {
        return new ClientResponse(c.getId(), c.getType().name(), c.getName(), c.getDocument(), c.getEmail(),
                c.getPhone(), c.getAddress(), c.getCity(), c.isActive(), missionCount,
                c.getUser() != null ? c.getUser().getEmail() : null);
    }
}
