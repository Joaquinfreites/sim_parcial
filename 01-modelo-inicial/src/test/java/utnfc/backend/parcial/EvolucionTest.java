package utnfc.backend.parcial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EvolucionTest {
    @Test
    void agregaGarantiaYRepuestos() {
        var reparacion = Reparacion.desdeCampos(new String[] {
                "P", "A", "celular", "Nexo", "1", "5", "ENTREGADA", "0", "SI", "100", "NORMAL"
        });

        assertTrue(reparacion.tieneGarantia());
        assertEquals(3800, reparacion.importe());
    }

    @Test
    void reconoceEntregada() {
        var reparacion = Reparacion.desdeCampos(new String[] {
                "P", "A", "celular", "Nexo", "1", "1", "ENTREGADA", "0", "NO", "0","NORMAL"
        });

        assertEquals("ENTREGADA", reparacion.getEstado());
    }
}
