package utnfc.backend.parcial;

public abstract class Reparacion {
    private final String id;
    private final String cliente;
    private final String dispositivo;
    private final String marca;
    private final String estado;
    private final int prioridad;
    private final int horas;
    private final double costoBase;
    private final String garantia;
    private final int repuestos;
    private final String modalidad;

    public Reparacion(String id, String cliente, String dispositivo, String marca,
            int prioridad, int horas, String estado, double costoBase , String garantia, int repuestos, String modalidad ) {
        if (id == null || id.isBlank() || cliente == null || cliente.isBlank()
                || prioridad < 1 || prioridad > 3 || horas <= 0 || costoBase < 0  || repuestos < 0 || modalidad == null) {
            throw new IllegalArgumentException("datos invalidos");
        }
        this.id = id.strip();
        this.cliente = cliente.strip();
        this.dispositivo = dispositivo.strip();
        this.marca = marca.strip();
        this.prioridad = prioridad;
        this.horas = horas;
        this.estado = estado.strip();
        this.costoBase = costoBase;
        this.garantia = garantia.strip();
        this.repuestos = repuestos;
        this.modalidad = modalidad.strip();
    }

    public static Reparacion desdeCampos(String[] campos) {
        if (campos.length != 11) {
            throw new IllegalArgumentException("columnas incorrectas");
        }
        return new ReparacionNormal(campos[0], campos[1], campos[2], campos[3],
                Integer.parseInt(campos[4]), Integer.parseInt(campos[5]), campos[6],
                Double.parseDouble(campos[7]),campos[8], Integer.parseInt(campos[9]), campos[10]);
    }

    public abstract  void calcularImporte();

    public double importe() {
        return costoBase + horas * 850 + prioridad * 300;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public String getMarca() {
        return marca;
    }

    public String getEstado() {
        return estado;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getHoras() {
        return horas;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public boolean tieneGarantia() {
            return "SI".equals(garantia);
    }

    public double getRepuestos() {
        return repuestos;
    }

    public String getModalidad() {
        return modalidad;
    }
}
