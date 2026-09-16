package utnfc.backend.parcial;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Path ruta = Path.of(args.length == 0 ? "datos/datos-parcial.csv" : args[0]);
        ResultadoParseo resultado = new ParserReparaciones().leer(ruta);
        Taller taller = new Taller(resultado.getReparaciones());

        System.out.println("Leidas=" + resultado.getLeidas()
                + " procesadas=" + resultado.getReparaciones().size()
                + " descartadas=" + resultado.getDescartadas()
                + " invalidas=" + resultado.getInvalidas());
        System.out.println("Facturacion=" + taller.facturacion());
        System.out.println("Por marca=" + taller.porMarca());

        if (!resultado.getErrores().isEmpty()) {
            System.out.println("Errores=" + resultado.getErrores());
        }
    }
}
