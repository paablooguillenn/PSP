"""Módulo que contiene la clase Usuario (proceso) que envía trabajos de impresión."""
import multiprocessing
import time
import random
from impresora import Impresora


class Usuario(multiprocessing.Process):
    """Proceso que representa a un usuario que envía trabajos de impresión.

    Cada Usuario crea varios trabajos y, para cada uno, intenta adquirir
    el semáforo para usar la impresora. Se utiliza try/finally para garantizar
    que el semáforo se libera incluso si ocurre un error.
    """

    def __init__(self, usuario_id, sem, trabajos=None):
        super().__init__()
        self.usuario_id = usuario_id
        self.sem = sem
        # Si no se especifican trabajos, generar 2 por defecto
        if trabajos is None:
            trabajos = ["Trabajo-1", "Trabajo-2"]
        self.trabajos = trabajos

    def run(self):
        """Ejecución del proceso Usuario: intenta imprimir cada trabajo."""
        for trabajo in self.trabajos:
            # Mensaje indicando que intenta acceder a la impresora
            print(f"[{self.usuario_id}] Esperando para imprimir {trabajo}...", flush=True)

            # Adquirir el semáforo (exclusión mutua para la impresora)
            self.sem.acquire()
            try:
                print(f"[{self.usuario_id}] Accediendo a la impresora para imprimir {trabajo}", flush=True)
                duracion = random.uniform(1.0, 3.0)
                Impresora.imprimir(self.usuario_id, trabajo, duracion)
                print(f"[{self.usuario_id}] Ha terminado de imprimir {trabajo}", flush=True)
            finally:
                # Asegurar que siempre se libera el semáforo
                self.sem.release()

                # Pequeña pausa aleatoria antes de preparar el siguiente trabajo
                time.sleep(random.uniform(0.1, 0.5))
