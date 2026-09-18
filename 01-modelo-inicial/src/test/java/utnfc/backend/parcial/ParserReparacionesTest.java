package utnfc.backend.parcial;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class ParserReparacionesTest {
    @Test
    void leeDataset() {
        var resultado = assertDoesNotThrow(() -> new ParserReparaciones()
                .leer(Path.of("datos/datos-parcial.csv")));

        assertEquals(140, resultado.getLeidas());
        assertEquals(114, resultado.getReparaciones().size());
        assertEquals(14, resultado.getDescartadas());
    }
}
