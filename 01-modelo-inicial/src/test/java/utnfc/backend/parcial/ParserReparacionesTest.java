package utnfc.backend.parcial;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class ParserReparacionesTest {
    @Test
    void leeDataset() {
        var resultado = assertDoesNotThrow(() -> new ParserReparaciones()
                .leer(Path.of("C:\\Users\\freit\\IdeaProjects\\01-modelo-inicial\\01-modelo-inicial\\datos.csv")));

        assertEquals(60, resultado.getLeidas());
        assertEquals(54, resultado.getReparaciones().size());
        assertEquals(6, resultado.getDescartadas());
    }
}
