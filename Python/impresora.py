"""Módulo que contiene la clase Impresora.

La impresora no mantiene estado compartido; sólo simula la acción de imprimir.
"""
import time


class Impresora:
    """Representa la impresora física. No mantiene estado compartido.

    Método:
        imprimir(usuario_id, trabajo, duracion): simula la impresión.
    """

    @staticmethod
    def imprimir(usuario_id, trabajo, duracion):
        """Simula la impresión de un trabajo.

        Args:
            usuario_id: identificador del usuario que imprime.
            trabajo: nombre del trabajo a imprimir.
            duracion: tiempo en segundos que dura la impresión.
        """
        print(f"[{usuario_id}] Impresora: Iniciando impresión de {trabajo} ({duracion:.2f}s)", flush=True)
        time.sleep(duracion)
        print(f"[{usuario_id}] Impresora: Finalizada impresión de {trabajo}", flush=True)
