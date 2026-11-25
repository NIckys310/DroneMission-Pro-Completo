// ============================================================================================
// DOCUMENTACIÓN · DroneMissionApplication.java
//
// QUÉ ES:        El botón de encendido del backend.
// QUÉ HACE:      Arranca Spring Boot, activa las tareas programadas
//                (@EnableScheduling: el drone virtual cada 2 s y la revisión diaria de
//                licencias) y lee las propiedades de application.yml.
// SI LO CAMBIAS: Si quitas @EnableScheduling, el drone virtual deja de moverse y las
//                licencias no se revisan. Casi nunca hay que tocarlo.
// ============================================================================================
package com.dronemission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Punto de entrada de DroneMission Pro.
 * @EnableScheduling activa las tareas programadas (simulador de telemetría
 * y verificación diaria de licencias).
 */
@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
public class DroneMissionApplication {

    /**
     * Main.
     * @param args args (String[])
     */
    public static void main(String[] args) {
        SpringApplication.run(DroneMissionApplication.class, args);
    }
}
