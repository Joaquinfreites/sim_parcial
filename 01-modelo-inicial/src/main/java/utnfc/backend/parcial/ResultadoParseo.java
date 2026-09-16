package utnfc.backend.parcial;

import java.util.List;

public class ResultadoParseo {
    private final List<Reparacion> reparaciones;
    private final int leidas;
    private final int descartadas;
    private final List<String> errores;

    public ResultadoParseo(List<Reparacion> reparaciones, int leidas, int descartadas,
            List<String> errores) {
        this.reparaciones = List.copyOf(reparaciones);
        this.leidas = leidas;
        this.descartadas = descartadas;
        this.errores = List.copyOf(errores);
    }

    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    public int getLeidas() {
        return leidas;
    }

    public int getDescartadas() {
        return descartadas;
    }

    public int getInvalidas() {
        return errores.size();
    }

    public List<String> getErrores() {
        return errores;
    }
}
