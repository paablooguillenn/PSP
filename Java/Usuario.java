import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Usuario {
    private final int idUsuario;
    private final int numTrabajos;
    private final int puerto;

    public Usuario(int idUsuario, int numTrabajos, int puerto) {
        this.idUsuario = idUsuario;
        this.numTrabajos = numTrabajos;
        this.puerto = puerto;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Se requiere el ID de usuario como argumento");
            System.exit(1);
        }

        int idUsuario = Integer.parseInt(args[0]);
        int numTrabajos = args.length > 1 ? Integer.parseInt(args[1]) : 2;
        int puerto = args.length > 2 ? Integer.parseInt(args[2]) : 5000;

        Usuario usuario = new Usuario(idUsuario, numTrabajos, puerto);
        usuario.ejecutarTrabajos();
    }

    public void ejecutarTrabajos() {
        try {
            for (int i = 1; i <= numTrabajos; i++) {
                // Simular tiempo entre trabajos (antes de solicitar la impresora)
                Thread.sleep((long) (Math.random() * 1500 + 500));

                String trabajo = "Trabajo-" + i;
                System.out.println("[Usuario-" + idUsuario + "] Accediendo a la impresora para imprimir " + trabajo);

                // Conectar con la impresora (servidor)
             try (Socket socket = new Socket("localhost", puerto);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    // Enviar petición de impresión: formato simple PRINT:id:Trabajo-1
                    out.println("PRINT:" + idUsuario + ":" + trabajo);

                    // Esperar respuesta del servidor (cuando termine la impresión)
                    String respuesta = in.readLine();
                    if ("DONE".equals(respuesta)) {
                        System.out.println("[Usuario-" + idUsuario + "] Ha terminado de imprimir " + trabajo);
                    } else {
                        System.out.println("[Usuario-" + idUsuario + "] Respuesta inesperada de la impresora: " + respuesta);
                    }
                } catch (IOException e) {
                    System.err.println("[Usuario-" + idUsuario + "] Error comunicando con la impresora: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
