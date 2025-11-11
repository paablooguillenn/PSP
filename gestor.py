"""Módulo GestorRestaurante: orquesta la simulación y ejecuta la aplicación.

Contiene las utilidades timestamp y dummy_lock internamente para mantener el
proyecto con cuatro clases/archivos principales.
"""
import multiprocessing
from multiprocessing import Process
from datetime import datetime

from cliente import Cliente
from cocinero import Cocinero
from repartidor import Repartidor


def timestamp():
    return datetime.now().strftime('%H:%M:%S')


class dummy_lock:
    def __enter__(self):
        return None

    def __exit__(self, exc_type, exc, tb):
        return False


class GestorRestaurante:
    def __init__(self, num_pedidos=5, num_cocineros=1, num_repartidores=1):
        self.num_pedidos = num_pedidos
        self.num_cocineros = max(1, num_cocineros)
        self.num_repartidores = max(1, num_repartidores)

        # Colas para comunicación entre procesos
        self.cola_pedidos = multiprocessing.Queue()
        self.cola_listos = multiprocessing.Queue()

        # Lock opcional para imprimir sin solapamientos
        self.print_lock = multiprocessing.Lock()

        # Contenedores para procesos
        self.clientes = []
        self.cocineros = []
        self.repartidores = []

    def run(self):
        try:
            cliente = Cliente(self.num_pedidos, self.cola_pedidos, num_cocineros=self.num_cocineros, print_lock=self.print_lock)
            self.clientes.append(cliente)
            cliente.start()

            for cid in range(self.num_cocineros):
                c = Cocinero(cid, self.cola_pedidos, self.cola_listos, num_cocineros=self.num_cocineros, num_repartidores=self.num_repartidores, print_lock=self.print_lock)
                self.cocineros.append(c)
                c.start()

            for rid in range(self.num_repartidores):
                r = Repartidor(rid, self.cola_listos, print_lock=self.print_lock)
                self.repartidores.append(r)
                r.start()

            for p in self.clientes:
                p.join()
            for p in self.cocineros:
                p.join()
            for p in self.repartidores:
                p.join()

            with self.print_lock:
                print(f'[{timestamp()}] [Gestor] Todos los procesos finalizados. Resumen: generados={self.num_pedidos}, preparados={self.num_pedidos}, entregados={self.num_pedidos}.')

        except Exception as e:
            with self.print_lock:
                print(f'[{timestamp()}] [Gestor] Error en la ejecución: {e}')


if __name__ == '__main__':
    multiprocessing.freeze_support()

    # Parámetros configurables
    NUM_PEDIDOS = 7
    NUM_COCINEROS = 2
    NUM_REPARTIDORES = 2

    gestor = GestorRestaurante(num_pedidos=NUM_PEDIDOS, num_cocineros=NUM_COCINEROS, num_repartidores=NUM_REPARTIDORES)
    gestor.run()
