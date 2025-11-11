import java.time.LocalTime;

/**
 * Cliente.java
 * Genera pedidos y los escribe en stdout para que el Cocinero los lea desde stdin.
 * Mensajes de estado en stderr.
 */
public class Cliente {
    public static void main(String[] args) {
        int numPedidos = 5;
        if (args.length > 0) {
            try { numPedidos = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }

        try {
            for (int i = 1; i <= numPedidos; i++) {
                String pedido = "Pedido-" + i;
                System.err.println("[Cliente] " + LocalTime.now() + " Generando " + pedido);
                System.out.println(pedido);
                Thread.sleep(800);
            }

            // Señal de finalización
            System.out.println("FIN");
            System.err.println("[Cliente] " + LocalTime.now() + " Todos los pedidos enviados. Señal FIN enviada.");
        } catch (InterruptedException e) {
            System.err.println("[Cliente] Error: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}