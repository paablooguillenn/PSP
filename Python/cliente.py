"""Módulo Cliente: genera pedidos y los coloca en la cola de pedidos.

Nota: Este módulo contiene pequeñas utilidades locales (timestamp y dummy_lock)
para evitar dependencias externas y mantener la aplicación con exactamente
cuatro clases separadas en archivos distintos.
"""
from multiprocessing import Process
import time
import random
from datetime import datetime


def timestamp():
    return datetime.now().strftime('%H:%M:%S')


class dummy_lock:
    def __enter__(self):
        return None

    def __exit__(self, exc_type, exc, tb):
        return False


class Cliente(Process):
    def __init__(self, num_pedidos, cola_pedidos, num_cocineros=1, print_lock=None):
        super().__init__()
        self.num_pedidos = num_pedidos
        self.cola_pedidos = cola_pedidos
        self.num_cocineros = num_cocineros
        self.print_lock = print_lock

    def run(self):
        try:
            for i in range(1, self.num_pedidos + 1):
                pedido = f'Pedido-{i}'
                with (self.print_lock or dummy_lock()):
                    print(f'[{timestamp()}] [Cliente] Generando {pedido}')
                self.cola_pedidos.put(pedido)
                time.sleep(random.uniform(0.1, 0.4))

            for _ in range(self.num_cocineros):
                self.cola_pedidos.put(None)
            with (self.print_lock or dummy_lock()):
                print(f'[{timestamp()}] [Cliente] Enviados centinelas. Fin de generación.')

        except Exception as e:
            with (self.print_lock or dummy_lock()):
                print(f'[{timestamp()}] [Cliente] Error: {e}')
