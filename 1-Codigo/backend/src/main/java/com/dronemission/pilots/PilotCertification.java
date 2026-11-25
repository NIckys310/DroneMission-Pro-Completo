// ============================================================================================
// DOCUMENTACIÓN · pilots/PilotCertification.java
//
// QUÉ ES:        Una certificación del piloto.
// QUÉ HACE:      Se guarda DENTRO del documento del piloto (campo "certificaciones").
// SI LO CAMBIAS: Campo nuevo: aquí, en CertificationDto y en PilotFormDialog.tsx.
// ============================================================================================
package com.dronemission.pilots;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Certificación obtenida por un piloto (relación 1:N con Pilot).
 *
 * MongoDB: se guarda EMBEBIDA dentro del documento del piloto (campo "certificaciones"),
 * porque la certificación pertenece solo a ese piloto (composición).
 */
@Getter
@Setter
@NoArgsConstructor
public class PilotCertification {

    // Número de la certificación dentro del piloto (1, 2, 3...). Lo asigna Pilot.replaceCertifications()
    private Long id;

    /** Atributo name (String): nombre. */
    @Field("nombre")
    private String name;

    /** Atributo issuer (String): entidad emisora. */
    @Field("entidad_emisora")
    private String issuer;

    /** Atributo issuedOn (LocalDate): emitido el. */
    @Field("fecha_emision")
    private LocalDate issuedOn;

    /** Atributo expiresOn (LocalDate): vence el. */
    @Field("fecha_vencimiento")
    private LocalDate expiresOn;
}
