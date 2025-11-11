import java.util.ArrayList;
import java.util.List;

public class Cocina {
    private final List<Pedido> pedidosPendientes;
    private final List<Cocinero> cocineros;
    private int pedidosProcesados = 0;
    private final int totalPedidos;

    public Cocina(int numCocineros, List<Pedido> pedidos) {
        this.pedidosPendientes = new ArrayList<>(pedidos);
        this.cocineros = new ArrayList<>();
        this.totalPedidos = pedidos.size();

        // Crear los cocineros
        for (int i = 1; i <= numCocineros; i++) {
            cocineros.add(new Cocinero("Cocinero-" + i, this));
        }
    }

    public synchronized Pedido tomarPedido() {
        if (pedidosPendientes.isEmpty()) {
            pedidosProcesados++;
            if (pedidosProcesados == cocineros.size()) {
                System.out.println("Todos los pedidos han sido procesados.");
            }
            return null;
        }
        return pedidosPendientes.remove(0);
    }

    public void comenzarPreparacion() {
        for (Cocinero cocinero : cocineros) {
            cocinero.start();
        }

        // Esperar a que todos los cocineros terminen
        for (Cocinero cocinero : cocineros) {
            try {
                cocinero.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error esperando a cocinero: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // Crear lista de pedidos
        List<Pedido> pedidos = new ArrayList<>();
        String[] platos = {"Pizza", "Hamburguesa", "Pasta", "Ensalada", "Sopa", "Paella"};
        
        for (int i = 0; i < platos.length; i++) {
            pedidos.add(new Pedido(i + 1, platos[i]));
        }

        // Crear cocina con 3 cocineros
        Cocina cocina = new Cocina(3, pedidos);
        
        // Comenzar la preparación de pedidos
        cocina.comenzarPreparacion();
    }
}