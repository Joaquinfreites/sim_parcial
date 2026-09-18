package utnfc.backend.parcial;

public class ReparacionExpress extends Reparacion {

    public ReparacionExpress(String id, String cliente, String dispositivo, String marca, int prioridad, int horas, String estado, double costoBase, String garantia, int repuestos, String modalidad) {
        super(id, cliente, dispositivo, marca, prioridad, horas, estado, costoBase, garantia, repuestos, modalidad);
    }

    @Override
    public double importe() {
        int manoDeObra = horas * 850;
        double descuento = 0.0;
        int recargoPrioridad = prioridad * 300;
        if (garantia.equals("SI")) {
            descuento = manoDeObra * 0.20;
        }
        double importeNormal = costoBase + horas * 850 + prioridad * 300 + repuestos - descuento;
        double importeExpress= importeNormal * 1.25;
        return importeExpress;
    }
}