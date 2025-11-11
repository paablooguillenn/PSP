"""Módulo Cocinero: consume pedidos de cola_pedidos, los prepara y los pone en cola_listos."""
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


class Cocinero(Process):
    def __init__(self, cook_id, cola_pedidos, cola_listos, num_cocineros=1, num_repartidores=1, print_lock=None):
        super().__init__()
        self.cook_id = cook_id
        self.cola_pedidos = cola_pedidos
        self.cola_listos = cola_listos
        self.num_cocineros = num_cocineros
        self.num_repartidores = num_repartidores
        self.print_lock = print_lock

    def run(self):
        try:
            while True:
                pedido = self.cola_pedidos.get()
                if pedido is None:
                    base = self.num_repartidores // max(1, self.num_cocineros)
                    remainder = self.num_repartidores % max(1, self.num_cocineros)
                    to_forward = base + (1 if self.cook_id < remainder else 0)
                    with (self.print_lock or dummy_lock()):
                        print(f'[{timestamp()}] [Cocinero-{self.cook_id}] Recibido centinela. Reenviando {to_forward} centinela(s) y finalizando.')
                    for _ in range(to_forward):
                        self.cola_listos.put(None)
                    break

                with (self.print_lock or dummy_lock()):
                    print(f'[{timestamp()}] [Cocinero-{self.cook_id}] Tomó {pedido}. Preparando...')
                time.sleep(random.uniform(0.5, 2.0))
                with (self.print_lock or dummy_lock()):
                    print(f'[{timestamp()}] [Cocinero-{self.cook_id}] {pedido} listo.')
                self.cola_listos.put(pedido)

        except Exception as e:
            with (self.print_lock or dummy_lock()):
                print(f'[{timestamp()}] [Cocinero-{self.cook_id}] Error: {e}')
