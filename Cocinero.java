import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalTime;

/**
 * Cocinero.java
 * Lee pedidos desde stdin (escritos por Cliente), los procesa y escribe pedidos listos en stdout
 * para que el Repartidor los lea. Mensajes de estado en stderr.
 */
public class Cocinero {
    public static void main(String[] args) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(System.out, true)
        ) {
            String pedido;
            while ((pedido = in.readLine()) != null) {
                if ("FIN".equals(pedido)) {
                    System.err.println("[Cocinero] " + LocalTime.now() + " Recibida señal FIN. Terminando.");
                    // Reenviar FIN para que Repartidor también termine
                    out.println("FIN");
                    break;
                }

                System.err.println("[Cocinero] " + LocalTime.now() + " Preparando " + pedido + "...");
                try { Thread.sleep(1500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.err.println("[Cocinero] " + LocalTime.now() + " " + pedido + " listo.");
                out.println(pedido + "-Listo");
            }
        } catch (Exception e) {
            System.err.println("[Cocinero] Error: " + e.getMessage());
        }
    }
}