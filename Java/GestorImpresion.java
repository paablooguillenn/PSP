import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GestorImpresion {
    private static final int NUM_USUARIOS = 3;
    private static final int TRABAJOS_POR_USUARIO = 2;
    private static final int PUERTO_IMPRESORA = 5000;

    public static void main(String[] args) {
        List<Process> procesos = new ArrayList<>();

        try {
            // 1) Iniciar el proceso Impresora (servidor)
            ProcessBuilder servidorPb = new ProcessBuilder("java", "Impresora", String.valueOf(PUERTO_IMPRESORA));
            servidorPb.inheritIO();
            Process servidor = servidorPb.start();

            // Dar un pequeño tiempo para que el servidor arranque
            Thread.sleep(500);

            // 2) Crear y ejecutar procesos de usuario pasándoles el puerto
            for (int i = 1; i <= NUM_USUARIOS; i++) {
                ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "Usuario",
                    String.valueOf(i),
                    String.valueOf(TRABAJOS_POR_USUARIO),
                    String.valueOf(PUERTO_IMPRESORA)
                );
                pb.inheritIO();
                Process proceso = pb.start();
                procesos.add(proceso);
            }

            // 3) Esperar a que todos los procesos usuario terminen
            for (Process proceso : procesos) {
                proceso.waitFor();
            }

            // 4) Solicitar shutdown al servidor de impresora
            try (Socket socket = new Socket("localhost", PUERTO_IMPRESORA);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out.println("SHUTDOWN");
                String resp = in.readLine();
                if ("BYE".equals(resp)) {
                    System.out.println("Servidor de impresora detenido correctamente.");
                }
            } catch (IOException e) {
                System.err.println("Error al solicitar shutdown al servidor: " + e.getMessage());
            }

            // Esperar a que el proceso servidor termine
            servidor.waitFor();

            System.out.println("\nTodos los trabajos han sido impresos.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}