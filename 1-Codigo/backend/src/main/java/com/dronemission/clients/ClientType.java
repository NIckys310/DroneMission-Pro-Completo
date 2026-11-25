// ============================================================================================
// DOCUMENTACIÓN · clients/ClientType.java
//
// QUÉ ES:        Una lista cerrada de valores (ClientType) usada en los clientes.
// QUÉ HACE:      Solo se permiten estos valores; cualquier otro da error.
// SI LO CAMBIAS: Valor nuevo: agrégalo aquí y en el frontend (types/index.ts y
//                utils/labels.ts).
// ============================================================================================
package com.dronemission.clients;

public enum ClientType {
    PERSONA, EMPRESA
}
