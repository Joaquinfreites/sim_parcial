package utnfc.backend.parcial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ReparacionTest {
    @Test
    void calculaImporte() {
        assertEquals(6950, new Reparacion(
                "R1", "Ana", "celular", "Nexo", 2, 5, "ABIERTA", 2100).importe());
    }

    @Test
    void rechazaPrioridad() {
        assertThrows(IllegalArgumentException.class, () -> new Reparacion(
                "R1", "Ana", "celular", "Nexo", 4, 2, "ABIERTA", 1));
    }
}
