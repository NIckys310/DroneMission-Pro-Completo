// ============================================================================================
// DOCUMENTACIÓN · drones/Drone.java
//
// QUÉ ES:        El molde de un drone (colección "drones").
// QUÉ HACE:      Guarda código, tipo, estado, batería, horas de vuelo, etc. Sabe si se
//                puede asignar (isAssignable).
// SI LO CAMBIAS: Si cambias isAssignable(), cambia cuándo un drone se puede usar en
//                una misión. Campo nuevo: aquí, en DroneRequest/Response, DroneMapper
//                y el frontend (droneForm.ts).
// ============================================================================================
package com.dronemission.drones;

import com.dronemission.common.persistence.BeforeCreate;
import com.dronemission.common.persistence.BeforeUpdate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/** Drone de la flota. Su estado lo cambian las misiones y los mantenimientos. */
// MongoDB: cada objeto se guarda como un documento en la colección "drones"
@Document(collection = "drones")
@Getter
@Setter
@NoArgsConstructor
public class Drone {

    // _id numérico: lo asigna SequenceGeneratorService (como el autoincremento de SQL)
    @Id
    private Long id;

    /** Atributo code (String): código legible (por ejemplo, DRN-001 o MSN-0001). Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("codigo")
    private String code;

    /** Atributo name (String): nombre. */
    @Field("nombre")
    private String name;


    /** Atributo manufacturer (String): fabricante. */
    @Field("fabricante")
    private String manufacturer;

    /** Atributo model (String): modelo. */
    @Field("modelo")
    private String model;

    

    /** Atributo serialNumber (String): número de serie (único). Único en la base (índice único). */
    @Indexed(unique = true)
    @Field("numero_serie")
    private String serialNumber;
    /** Atributo type (DroneType): tipo. */
    @Field("tipo")
    private DroneType type;
    /** Atributo status (DroneStatus): estado. */
    @Field("estado")
    private DroneStatus status = DroneStatus.DISPONIBLE;

    /** Atributo batteryCapacityMah (Integer): capacidad de la batería (mAh). */
    @Field("capacidad_bateria_mah")
    private Integer batteryCapacityMah;

    /** Porcentaje actual de batería (0-100). */
    @Field("bateria_actual")
    private Integer batteryLevel = 100;

    /** Atributo flightHours (BigDecimal): horas de vuelo acumuladas. */
    @Field("horas_vuelo")
    private BigDecimal flightHours = BigDecimal.ZERO;

    /** Atributo maxSpeedKmh (Integer): velocidad máxima (km/h). */
    @Field("velocidad_maxima_kmh")
    private Integer maxSpeedKmh;

    /** Atributo autonomyMinutes (Integer): autonomía de vuelo (minutos). */
    @Field("autonomia_minutos")
    private Integer autonomyMinutes;

    /** Atributo acquiredOn (LocalDate): fecha de adquisición. */
    @Field("fecha_adquisicion")
    private LocalDate acquiredOn;

    /** Atributo lastMaintenanceOn (LocalDate): fecha del último mantenimiento. */
    @Field("ultimo_mantenimiento")
    private LocalDate lastMaintenanceOn;

    /** Atributo nextMaintenanceOn (LocalDate): fecha del próximo mantenimiento. */
    @Field("proximo_mantenimiento")
    private LocalDate nextMaintenanceOn;

    /** Atributo description (String): descripción. */
    @Field("descripcion")
    private String description;

    /** Atributo active (boolean): true si está activo (baja lógica). */
    @Field("activo")
    private boolean active = true;

    /** Atributo createdAt (LocalDateTime): fecha y hora de creación. */
    @Field("creado_en")
    private LocalDateTime createdAt;

    /** Atributo updatedAt (LocalDateTime): fecha y hora de la última actualización. */
    @Field("actualizado_en")
    private LocalDateTime updatedAt;

    // Se ejecuta antes de guardar un documento nuevo (lo llama MongoLifecycleListener)
    @BeforeCreate
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    // Se ejecuta antes de actualizar un documento existente (lo llama MongoLifecycleListener)
    @BeforeUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /** Un drone se puede asignar si no está en mantenimiento ni fuera de servicio (reglas 1 y 2). */
    public boolean isAssignable() {
        return active && status != DroneStatus.EN_MANTENIMIENTO && status != DroneStatus.FUERA_DE_SERVICIO;
    }

    /**
     * Indica si tiene batería en least.
     * @param percentage percentage (int)
     * @return boolean
     */
    public boolean hasBatteryAtLeast(int percentage) {
        return batteryLevel != null && batteryLevel >= percentage;
    }

    /**
     * Agrega horas de vuelo acumuladas.
     * @param hours horas (BigDecimal)
     */
    public void addFlightHours(BigDecimal hours) {
        flightHours = flightHours.add(hours);
    }
}
