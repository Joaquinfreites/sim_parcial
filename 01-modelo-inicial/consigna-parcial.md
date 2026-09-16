# Parcial práctico: garantía y entrega de reparaciones

**Tiempo orientativo: 45 minutos.** Partir exactamente del proyecto preparcial. Se entregan esta consigna, `datos-parcial.csv` y la carpeta `tests-nuevos`. Mantener los tests existentes.

El archivo del parcial contiene 140 filas de datos más encabezado y es un conjunto nuevo.

El taller técnico trabaja inicialmente con una orden de reparación abierta: recibe un dispositivo, estima las horas de trabajo, registra un costo base y asigna una prioridad. Esa orden todavía no representa necesariamente un trabajo terminado ni un importe final cobrado.

Durante la evolución, el taller necesita distinguir las reparaciones comunes de las reparaciones **EXPRESS**. Una reparación express es un servicio solicitado por un cliente que necesita recuperar el dispositivo con urgencia. No es solamente una etiqueta: implica una forma distinta de calcular el precio, porque el taller debe reorganizar su agenda y atender el trabajo con prioridad.

La reparación express debe conservar todos los datos y reglas comunes de una reparación, pero agregar su comportamiento específico. Por eso resulta natural modelarla como una especialización de `Reparacion`: ambas son reparaciones, pero una express calcula un importe diferente.

## Nuevo formato

El encabezado evolucionado es:

```text
id,cliente,dispositivo,marca,prioridad,horas,estado,costoBase,garantia,repuestos,modalidad
```

El parser debe conservar el formato inicial de ocho columnas y aceptar el nuevo formato de once columnas sin duplicar el proceso de carga. Cada fila debe tener exactamente el ancho correspondiente a su encabezado.

## Reglas nuevas

- `ABIERTA` y `LISTA` conservan el comportamiento del preparcial. `ABIERTA` indica que el taller aún está trabajando o esperando autorización; `LISTA` indica que el diagnóstico o trabajo principal está listo para continuar con la entrega.
- `ENTREGADA` indica que el cliente retiró el dispositivo. Es un estado válido y debe incorporarse a la colección porque forma parte del historial y de la facturación del taller.
- `CANCELADA` indica que el cliente no continuará con la reparación. Se descarta después de verificar el ancho de la fila, sin validar los demás campos operativos.
- `garantia` indica si el trabajo está cubierto por una garantía del taller o del vendedor. `SI` y `NO` son los únicos valores válidos.
- `repuestos` representa el costo de las piezas efectivamente utilizadas, por ejemplo una pantalla, una batería o un teclado. Es un importe numérico no negativo.
- `modalidad` indica cómo se agenda el trabajo. `NORMAL` crea una `Reparacion` y `EXPRESS` crea una `ReparacionExpress`.

La modalidad no reemplaza al estado: una reparación `EXPRESS` puede estar `ABIERTA`, `LISTA` o `ENTREGADA`. El estado indica en qué momento del proceso se encuentra la orden; la modalidad indica cómo fue contratada y cómo se calcula su importe.
- El costo de mano de obra se calcula como `horas * 850`.
- La prioridad agrega un cargo operativo de `prioridad * 300`: una prioridad mayor requiere reorganizar recursos y atención del taller.
- El importe normal de una reparación evolucionada se calcula sumando costo base, mano de obra y prioridad, y luego agregando los repuestos:

```text
importe anterior + repuestos
- 20% del costo de mano de obra si garantia = SI
```

La bonificación de garantía representa que el taller absorbe parte de la mano de obra cuando el problema está cubierto. Por eso se descuenta el 20% **solamente de la mano de obra**. No se descuenta de los repuestos, porque las piezas utilizadas deben pagarse; tampoco del costo base ni del cargo de prioridad.

Ejemplo: una reparación con costo base 1000, prioridad 2, 3 horas y repuestos por 200 tiene:

```text
mano de obra                  3 * 850       = 2550
prioridad                     2 * 300       =  600
costo base                                  = 1000
repuestos                                    =  200
importe sin garantía                         = 4350
bonificación de garantía       20% de 2550  =  510
importe con garantía                         = 3840
```

Una reparación express aplica el comportamiento común de `Reparacion` y agrega un recargo del 25% sobre el importe común calculado. El recargo existe porque el taller reorganiza la agenda y atiende el trabajo con urgencia. Se aplica una sola vez al importe total, después de calcular garantía, mano de obra y repuestos.

Por ejemplo, si el importe común es 3840, una reparación express cuesta `3840 * 1.25 = 4800`.

La solución docente modela este comportamiento con una clase `ReparacionExpress` que hereda de `Reparacion` y redefine el cálculo de `importe()`. La consigna evalúa el comportamiento; una solución alternativa con composición puede ser aceptada si mantiene las mismas reglas y no duplica la lógica común.

- Un estado desconocido, una garantía vacía/desconocida, una modalidad distinta de `NORMAL` o `EXPRESS`, repuestos negativos o un valor numérico no convertible vuelve inválida la fila.
- Una fila inválida no debe detener la lectura.

La precedencia es: primero verificar la cantidad de columnas; luego descartar `CANCELADA`; recién después validar estado, garantía, números y reglas del dominio.

## Resultado requerido

1. Integrar ambos formatos en un único parser.
2. Calcular correctamente importes iniciales y evolucionados.
3. Mantener la continuidad ante filas inválidas y el resumen de procesamiento.
4. Incorporar en `Taller` el total de reparaciones `ENTREGADA`.
5. Incorporar un agrupamiento o conteo por tipo de dispositivo. `dispositivo` y `marca` son datos diferentes: el primero identifica la clase de equipo y el segundo su fabricante.
6. Hacer que `Main` use por defecto el CSV del parcial y muestre resumen, facturación, entregadas y agrupamiento por dispositivo.
7. Conservar los tests iniciales y pasar los tests nuevos.

Se evalúan comportamiento observable, encapsulamiento y responsabilidades. Se aceptan ciclos o Streams, herencia, composición u otra solución razonable. No se exige una jerarquía ni una firma fija para las operaciones nuevas. La lógica no debe concentrarse en `Main`.

## Pruebas y entrega

Desde la raíz de la copia del proyecto inicial:

```sh
cp -R tests-nuevos/utnfc src/test/java/
cp datos-parcial.csv datos/datos-parcial.csv
mvn test
java -cp target/classes utnfc.backend.parcial.Main datos/datos-parcial.csv
```

Los tests nuevos deben compilar contra el proyecto inicial y fallar funcionalmente por la falta del formato ampliado, sin depender de una API exclusiva de la solución docente. Revisar además el `Main` con las 140 filas.

Entregar un ZIP con `pom.xml`, `src/` y `datos/`, excluyendo `target/`, `.git/` y archivos del IDE. Incluir `DECISIONES.md` con 3 a 5 líneas sobre la solución elegida y eventuales pendientes. Subirlo en el ítem 10 del cuestionario.

Distribución sugerida: 5 minutos de lectura, 25 de cambios, 10 de pruebas y 5 de revisión y empaquetado.
