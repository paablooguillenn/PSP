import threading
from pedido import Pedido
from cocinero import Cocinero
import os

class Cocina:
    def __init__(self):
        self.pedidos = []
        self.pedidos_lock = threading.Lock()
        self.log_lock = threading.Lock()
        
        # Crear archivo log si no existe
        if not os.path.exists("log_pedidos.txt"):
            open("log_pedidos.txt", "w").close()
    
    def agregar_pedido(self, pedido):
        self.pedidos.append(pedido)
    
    def iniciar_cocineros(self, num_cocineros):
        cocineros = []
        for i in range(num_cocineros):
            cocinero = Cocinero(f"Cocinero {i+1}", self)
            cocineros.append(cocinero)
            cocinero.start()
        
        # Esperar a que todos los cocineros terminen
        for cocinero in cocineros:
            cocinero.join()

def main():
    # Crear la cocina
    cocina = Cocina()
    
    # Crear pedidos
    platos = [
        "Pasta Carbonara",
        "Pizza Margherita",
        "Ensalada César",
        "Sopa de Tomate",
        "Filete de Salmón",
        "Risotto de Champiñones"
    ]
    
    # Agregar pedidos a la cocina
    for i, plato in enumerate(platos, 1):
        pedido = Pedido(i, plato)
        cocina.agregar_pedido(pedido)
    
    # Iniciar 3 cocineros
    print("Iniciando la preparación de pedidos...")
    cocina.iniciar_cocineros(3)
    print("\nTodos los pedidos han sido procesados.")

if __name__ == "__main__":
    main()