package utnfc.backend.parcial;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParserReparaciones {
    public ResultadoParseo leer(Path ruta) throws IOException {
        List<Reparacion> reparaciones = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int leidas = 0;
        int descartadas = 0;
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            if (!"id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase,garantia,repuestos,modalidad".equals(lector.readLine())) {
                throw new IllegalArgumentException("encabezado incorrecto");
            }
            String linea;
            while ((linea = lector.readLine()) != null) {
                leidas++;
                try {
                    String[] campos = Arrays.stream(linea.split(",", -1))
                            .map(String::strip)
                            // Saca los espacios -> Strip -> Los espacios de más en el csv
                            .collect(Collectors.toList())
                            .toArray(new String[0]);
                    if (campos.length != 11) {
                        if(campos.length != 8){
                            throw new IllegalArgumentException("columnas incorrectas");
                        }
                        throw new IllegalArgumentException("columnas incorrectas");
                    }
                    if (campos[6].equals("CANCELADA")) {
                        descartadas++;
                        continue;
                    }
                    if (!campos[6].equals("ABIERTA") && !campos[6].equals("LISTA")) {
                        throw new IllegalArgumentException("estado desconocido");
                    }
                    if  (!campos[8].equals("SI") && !campos[8].equals("N0")) {
                        descartadas++;
                        continue;
                    }
                    if (Integer.parseInt(campos[9]) <= 0){
                        descartadas++;
                        continue;
                    }
                    if (!campos[10].equals("NORMAL") && !campos[10].equals("EXPRESS")) {
                        descartadas++;
                        continue;
                    }
                    if(campos[10].equals("NORMAL")) {
                        Reparacion reparacion = new Reparacion(campos[0], campos[1], campos[2], campos[3],
                                Integer.parseInt(campos[4]), Integer.parseInt(campos[5]), campos[6],
                                Double.parseDouble(campos[7]),campos[8], Integer.parseInt(campos[9]), campos[10]);
                    }
                    if(campos[10].equals("EXPRESS")){
                        Reparacion reparacion = new ReparacionExpress(campos[0], campos[1], campos[2], campos[3],
                                Integer.parseInt(campos[4]), Integer.parseInt(campos[5]), campos[6],
                                Double.parseDouble(campos[7]), campos[8], Integer.parseInt(campos[9]), campos[10]);
                    }
                    reparaciones.add(Reparacion.desdeCampos(campos));
                } catch (IllegalArgumentException error) {
                    errores.add("Linea " + (leidas + 1) + ": " + error.getMessage());
                }
            }
        }
        return new ResultadoParseo(reparaciones, leidas, descartadas, errores);
    }
}
