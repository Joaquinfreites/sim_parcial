package utnfc.backend.parcial;

public class ReparacionNormal extends Reparacion {
    public ReparacionNormal(String id, String cliente, String dispositivo, String marca, int prioridad, int horas, String estado, double costoBase, String garantia, int repuestos, String modalidad) {
        super(id, cliente, dispositivo, marca, prioridad, horas, estado, costoBase, garantia, repuestos, modalidad);
    }
    @Override
    public void calcularImporte() {

    }
}
