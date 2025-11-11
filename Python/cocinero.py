import threading
import time
import random
from datetime import datetime

class Cocinero(threading.Thread):
    def __init__(self, nombre, cocina):
        super().__init__()
        self.nombre = nombre
        self.cocina = cocina
    
    def preparar_pedido(self, pedido):
        # Simular tiempo de preparación entre 2 y 5 segundos
        tiempo_preparacion = random.uniform(2, 5)
        time.sleep(tiempo_preparacion)
        
        mensaje = f"{datetime.now()} - {self.nombre} ha preparado {pedido}"
        print(mensaje)
        
        # Registrar en el archivo log
        with self.cocina.log_lock:
            with open("log_pedidos.txt", "a", encoding="utf-8") as log:
                log.write(mensaje + "\n")
    
    def run(self):
        while True:
            # Intentar obtener un pedido con el lock
            with self.cocina.pedidos_lock:
                if not self.cocina.pedidos:
                    break  # No hay más pedidos, terminar
                pedido = self.cocina.pedidos.pop(0)  # Tomar el primer pedido
            
            # Preparar el pedido
            self.preparar_pedido(pedido)