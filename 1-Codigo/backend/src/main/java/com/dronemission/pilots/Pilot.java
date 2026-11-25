// ============================================================================================
// DOCUMENTACIÓN · pilots/Pilot.java
//
// QUÉ ES:        El molde de un piloto (colección "pilotos").
// QUÉ HACE:      Datos, licencia, estado y certificaciones. hasValidLicenseOn() dice
//                si su licencia sirve en una fecha.
// SI LO CAMBIAS: Si cambias hasValidLicenseOn(), cambia cuándo un piloto puede volar.
//                PilotLicenseTest te avisa.
// ============================================================================================
package com.dronemission.pilots;

import com.dronemission.users.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Piloto certificado que opera los drones. */
// MongoDB: cada objeto se guarda como un documento en la colección "pilotos"
@Document(collection = "pilotos")
@Getter
@Setter
@NoArgsConstructor
public class Pilot {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    /** Atributo firstName (String): nombre. */
    @Field("nombre")
    private String firstName;

    /** Atributo lastName (String): apellidos. */
    @Field("apellidos")
    private String lastName;

    /** Atributo document (String): documento. Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("documento")
    private String document;

    /** Atributo email (String): correo. Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("correo")
    private String email;

    /** Atributo phone (String): teléfono. */
    @Field("telefono")
    private String phone;

    /** Atributo licenseNumber (String): número de licencia de piloto. Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("numero_licencia")
    private String licenseNumber;

    /** Atributo licenseExpiresOn (LocalDate): fecha de vencimiento de la licencia. */
    @Field("vencimiento_licencia")
    private LocalDate licenseExpiresOn;

    /** Atributo flightHours (BigDecimal): horas de vuelo acumuladas. */
    @Field("horas_vuelo")
    private BigDecimal flightHours = BigDecimal.ZERO;
    /** Atributo status (PilotStatus): estado. */
    @Field("estado")
    private PilotStatus status = PilotStatus.DISPONIBLE;

    /** Atributo active (boolean): true si está activo (baja lógica). */
    @Field("activo")
    private boolean active = true;

    // Referencia a otro documento (DBRef): en Mongo se guarda {$ref, $id}
    @DBRef
    @Field("usuario_id")
    private User user;

    // Certificaciones EMBEBIDAS en el documento del piloto (composición)
    @Field("certificaciones")
    private List<PilotCertification> certifications = new ArrayList<>();

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Obtiene nombre completo.
     * @return String
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /** La licencia es válida si no ha vencido en la fecha indicada. */
    public boolean hasValidLicenseOn(LocalDate date) {
        return licenseExpiresOn != null && !licenseExpiresOn.isBefore(date);
    }

    /**
     * Agrega horas de vuelo acumuladas.
     * @param hours horas (BigDecimal)
     */
    public void addFlightHours(BigDecimal hours) {
        flightHours = flightHours.add(hours);
    }

    /** Recalcula el estado según la licencia, sin tocar a un piloto que está volando. */
    public void refreshLicenseStatus(LocalDate today) {
        if (status == PilotStatus.EN_MISION) {
            return;
        }
        if (!hasValidLicenseOn(today)) {
            status = PilotStatus.LICENCIA_VENCIDA;
        } else if (status == PilotStatus.LICENCIA_VENCIDA) {
            status = PilotStatus.DISPONIBLE;
        }
    }

    /**
     * Reemplaza certificaciones (embebidas en el piloto).
     * @param newCertifications certificaciones new (lista de PilotCertification)
     */
    public void replaceCertifications(List<PilotCertification> newCertifications) {
        certifications.clear();
        // Ordenadas de la más reciente a la más antigua (antes lo hacía @OrderBy de JPA)
        newCertifications.stream()
                .sorted(java.util.Comparator.comparing(PilotCertification::getIssuedOn,
                        java.util.Comparator.nullsLast(java.util.Comparator.reverseOrder())))
                .forEach(certifications::add);
        // Al estar embebidas no tienen secuencia propia: se numeran según su posición
        for (int i = 0; i < certifications.size(); i++) {
            certifications.get(i).setId((long) i + 1);
        }
    }
}
