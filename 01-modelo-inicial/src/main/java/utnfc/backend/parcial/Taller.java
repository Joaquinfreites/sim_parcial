package utnfc.backend.parcial;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Taller {
    private final List<Reparacion> reparaciones;

    public Taller(List<Reparacion> reparaciones) {
        this.reparaciones = List.copyOf(reparaciones);
    }

    public double facturacion() {
        return reparaciones.stream().mapToDouble(Reparacion::importe).sum();
    }

    public long contar(Predicate<Reparacion> criterio) {
        return reparaciones.stream().filter(criterio).count();
    }

    public Map<String, Long> porMarca() {
        return reparaciones.stream().collect(Collectors.groupingBy(
                Reparacion::getMarca, TreeMap::new, Collectors.counting()));
    }
}
