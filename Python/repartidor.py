"""Módulo Repartidor: consume pedidos de cola_listos y los entrega."""
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


class Repartidor(Process):
    def __init__(self, rider_id, cola_listos, print_lock=None):
        super().__init__()
        self.rider_id = rider_id
        self.cola_listos = cola_listos
        self.print_lock = print_lock

    def run(self):
        try:
            while True:
                pedido = self.cola_listos.get()
                if pedido is None:
                    with (self.print_lock or dummy_lock()):
                        print(f'[{timestamp()}] [Repartidor-{self.rider_id}] Recibido centinela. Fin de entregas.')
                    break

                with (self.print_lock or dummy_lock()):
                    print(f'[{timestamp()}] [Repartidor-{self.rider_id}] Recogió {pedido}. Entregando...')
                time.sleep(random.uniform(0.5, 1.5))
                with (self.print_lock or dummy_lock()):
                    print(f'[{timestamp()}] [Repartidor-{self.rider_id}] {pedido} entregado.')

        except Exception as e:
            with (self.print_lock or dummy_lock()):
                print(f'[{timestamp()}] [Repartidor-{self.rider_id}] Error: {e}')
