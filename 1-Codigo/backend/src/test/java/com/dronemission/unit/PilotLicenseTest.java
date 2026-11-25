// ============================================================================================
// DOCUMENTACIÓN · prueba PilotLicenseTest
//
// QUÉ ES:        Una prueba automática (no es parte del sistema, solo lo revisa).
// QUÉ HACE:      Prueba unitaria de la vigencia de la licencia del piloto.
// SI LO CAMBIAS: Si cambias una regla del sistema a propósito, actualiza esta prueba.
//                Si falla sin que lo esperes, rompiste algo. Se corre con ./mvnw test.
// ============================================================================================
package com.dronemission.unit;

import com.dronemission.pilots.Pilot;
import com.dronemission.pilots.PilotStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/** Regla: un piloto con licencia vencida queda en estado LICENCIA_VENCIDA. */
class PilotLicenseTest {

    @Test
    @DisplayName("Marca la licencia vencida y la libera al renovarla")
    void refreshesLicenseStatus() {
        Pilot pilot = new Pilot();
        pilot.setStatus(PilotStatus.DISPONIBLE);
        pilot.setLicenseExpiresOn(LocalDate.now().minusDays(1));
        pilot.refreshLicenseStatus(LocalDate.now());
        assertThat(pilot.getStatus()).isEqualTo(PilotStatus.LICENCIA_VENCIDA);

        pilot.setLicenseExpiresOn(LocalDate.now().plusYears(1));
        pilot.refreshLicenseStatus(LocalDate.now());
        assertThat(pilot.getStatus()).isEqualTo(PilotStatus.DISPONIBLE);
    }

    @Test
    @DisplayName("No cambia el estado de un piloto que está volando")
    void doesNotTouchFlyingPilot() {
        Pilot pilot = new Pilot();
        pilot.setStatus(PilotStatus.EN_MISION);
        pilot.setLicenseExpiresOn(LocalDate.now().minusDays(1));
        pilot.refreshLicenseStatus(LocalDate.now());
        assertThat(pilot.getStatus()).isEqualTo(PilotStatus.EN_MISION);
    }
}
