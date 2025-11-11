import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Impresora {
    private final Semaphore semaforo = new Semaphore(1);
    private final int puerto;
    private volatile boolean ejecutando = true;

    public Impresora(int puerto) {
        this.puerto = puerto;
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(puerto)) {
            System.out.println("[Impresora] Servidor iniciado en puerto " + puerto);

            while (ejecutando) {
                Socket cliente = server.accept();

                // Manejar la petición de forma secuencial en el mismo proceso (no crear hilos)
                try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                     PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {
                    String linea = in.readLine();
                    if (linea == null) {
                        continue;
                    }

                    if (linea.startsWith("PRINT:")) {
                        // Formato: PRINT:id:Trabajo-1
                        String[] partes = linea.split(":", 3);
                        int idUsuario = Integer.parseInt(partes[1]);
                        String trabajo = partes[2];

                        try {
                            semaforo.acquire();
                            System.out.println("[Impresora] Usuario-" + idUsuario + " - Iniciando " + trabajo);
                            // Simular impresión
                            Thread.sleep(1500);
                            System.out.println("[Impresora] Usuario-" + idUsuario + " - " + trabajo + " completado");
                            out.println("DONE");
                        } catch (InterruptedException e) {
                            out.println("ERROR");
                        } finally {
                            semaforo.release();
                        }

                    } else if (linea.equals("SHUTDOWN")) {
                        out.println("BYE");
                        ejecutando = false;
                    } else {
                        out.println("UNKNOWN");
                    }
                }
            }

            System.out.println("[Impresora] Servidor detenido.");
        }
    }

    public static void main(String[] args) {
        int puerto = args.length > 0 ? Integer.parseInt(args[0]) : 5000;
        Impresora servidor = new Impresora(puerto);
        try {
            servidor.start();
        } catch (IOException e) {
            System.err.println("[Impresora] Error al iniciar el servidor: " + e.getMessage());
        }
    }
}