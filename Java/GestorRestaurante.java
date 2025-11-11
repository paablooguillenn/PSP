import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * GestorRestaurante.java
 * Lanza los procesos Cliente, Cocinero y Repartidor y conecta sus streams
 * usando ProcessBuilder.startPipeline(...) para no usar hilos en la gestión.
 * Espera a que cada proceso termine y muestra un resumen final.
 */
public class GestorRestaurante {
    public static void main(String[] args) {
        int numPedidos = 5;
        if (args.length > 0) {
            try { numPedidos = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }

        // Usamos -cp . para asegurarnos de cargar las clases compiladas en el directorio actual
        ProcessBuilder clientePB = new ProcessBuilder("java", "-cp", ".", "Cliente", String.valueOf(numPedidos));
        ProcessBuilder cocineroPB = new ProcessBuilder("java", "-cp", ".", "Cocinero");
        ProcessBuilder repartidorPB = new ProcessBuilder("java", "-cp", ".", "Repartidor");

        // Mostrar errores de los procesos en la consola del gestor
        clientePB.redirectError(ProcessBuilder.Redirect.INHERIT);
        cocineroPB.redirectError(ProcessBuilder.Redirect.INHERIT);
        repartidorPB.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            // startPipeline conecta stdout->stdin entre los builders en el orden dado
            List<Process> processes = ProcessBuilder.startPipeline(Arrays.asList(clientePB, cocineroPB, repartidorPB));

            // Esperar a que terminen todos los procesos
            int i = 0;
            for (Process p : processes) {
                int rc = p.waitFor();
                System.out.println("[Gestor] Proceso " + (++i) + " finalizado con código: " + rc);
            }

            System.out.println("[Gestor] Todos los procesos finalizados correctamente.");
        } catch (IOException e) {
            System.err.println("[Gestor] Error E/S al iniciar procesos: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("[Gestor] Gestor interrumpido: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}