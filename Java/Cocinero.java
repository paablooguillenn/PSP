import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Cocinero extends Thread {
    private final String nombre;
    private final Cocina cocina;

    public Cocinero(String nombre, Cocina cocina) {
        this.nombre = nombre;
        this.cocina = cocina;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = cocina.tomarPedido();
            if (pedido == null) {
                break; // No hay más pedidos
            }
            
            prepararPedido(pedido);
        }
    }

    private void prepararPedido(Pedido pedido) {
        System.out.println(nombre + " está preparando: " + pedido);
        
        try {
            // Simulamos el tiempo de preparación (entre 1 y 3 segundos)
            Thread.sleep((long) (Math.random() * 2000 + 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // Registrar en el archivo log
        synchronized (Cocinero.class) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("log_pedidos.txt", true))) {
                writer.println(nombre + " ha completado " + pedido);
            } catch (IOException e) {
                System.err.println("Error al escribir en el log: " + e.getMessage());
            }
        }

        System.out.println(nombre + " ha terminado de preparar: " + pedido);
    }
}