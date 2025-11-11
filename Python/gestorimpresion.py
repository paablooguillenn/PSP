"""Script principal que crea el GestorImpresion y lanza la simulación.

Cumple los requisitos: usa multiprocessing.Process y multiprocessing.Semaphore
para coordinar el acceso exclusivo a la impresora por varios usuarios.
"""
import multiprocessing
import time
import random
from usuario import Usuario


class GestorImpresion:
    """Crea el semáforo y lanza varios procesos Usuario."""

    def __init__(self, num_usuarios=3, trabajos_por_usuario=2):
        self.num_usuarios = num_usuarios
        self.trabajos_por_usuario = trabajos_por_usuario
        # Semáforo binario: permite acceso exclusivo a la impresora
        self.semaforo = multiprocessing.Semaphore(1)
        self.procesos = []

    def crear_usuarios(self) -> None:
        """Crea instancias de Usuario (procesos) y las almacena."""
        for i in range(1, self.num_usuarios + 1):
            usuario_id = f"Usuario-{i}"
            trabajos = [f"Trabajo-{j}" for j in range(1, self.trabajos_por_usuario + 1)]
            p = Usuario(usuario_id=usuario_id, sem=self.semaforo, trabajos=trabajos)
            self.procesos.append(p)

    def iniciar(self) -> None:
        """Inicia todos los procesos Usuario y espera a que terminen."""
        print("[Gestor] Iniciando la simulación de impresión con semáforo binario.\n", flush=True)
        # Arrancar procesos
        for p in self.procesos:
            p.start()

        # Esperar a que terminen
        for p in self.procesos:
            p.join()

        print("\n[Gestor] Todos los trabajos han sido impresos.", flush=True)


if __name__ == "__main__":
    # Semilla para reproducibilidad en demostraciones
    random.seed(42)

    gestor = GestorImpresion(num_usuarios=4, trabajos_por_usuario=3)
    gestor.crear_usuarios()
    gestor.iniciar()
