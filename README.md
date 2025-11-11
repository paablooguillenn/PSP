🍽️ Sistema de Gestión de Restaurante
Sistema de simulación de restaurante que demuestra programación concurrente mediante la comunicación entre procesos. Implementado en Java y Python con arquitecturas diferentes pero funcionalmente equivalentes.

📋 Descripción
Este proyecto simula el flujo de trabajo de un restaurante con tres roles principales:

Cliente: Genera pedidos continuamente
Cocinero: Procesa y prepara los pedidos
Repartidor: Entrega los pedidos preparados

Ambas implementaciones demuestran patrones de comunicación entre procesos (IPC) y concurrencia.

🏗️ Arquitectura
Implementación Java

Comunicación: Pipes (stdin/stdout) mediante ProcessBuilder.startPipeline()
Patrón: Pipeline de procesos conectados secuencialmente
Procesos: Cliente → Cocinero → Repartidor

Implementación Python

Comunicación: Colas multiproceso (multiprocessing.Queue)
Patrón: Productor-consumidor con múltiples workers
Escalabilidad: Soporta múltiples cocineros y repartidores

🚀 Requisitos
Java

JDK: 9 o superior (requiere ProcessBuilder.startPipeline())
Sistema: Windows, Linux, macOS

Python

Versión: Python 3.6+
Módulos: Biblioteca estándar únicamente (no requiere pip)

📦 Instalación
Clonar el repositorio
bashgit clone https://github.com/tuusuario/restaurant-simulation.git
cd restaurant-simulation
Compilar Java
bashjavac Cliente.java Cocinero.java Repartidor.java GestorRestaurante.java
▶️ Uso
Versión Java
Ejecución básica (5 pedidos por defecto):
bashjava GestorRestaurante
Especificar número de pedidos:
bashjava GestorRestaurante 10
Salida esperada:
[Cliente] 14:23:01 Generando Pedido-1
[Cocinero] 14:23:01 Preparando Pedido-1...
[Cocinero] 14:23:02 Pedido-1 listo.
[Repartidor] 14:23:02 Entregando Pedido-1-Listo
...
[Gestor] Proceso 1 finalizado con código: 0
[Gestor] Todos los procesos finalizados correctamente.
Versión Python
Ejecución básica:
bashpython gestor.py
Configurar parámetros (editar en gestor.py):
pythonNUM_PEDIDOS = 7
NUM_COCINEROS = 2      # Múltiples cocineros en paralelo
NUM_REPARTIDORES = 2   # Múltiples repartidores en paralelo
Salida esperada:
[14:23:01] [Cliente] Generando Pedido-1
[14:23:01] [Cocinero-0] Tomó Pedido-1. Preparando...
[14:23:03] [Cocinero-0] Pedido-1 listo.
[14:23:03] [Repartidor-0] Recogió Pedido-1. Entregando...
[14:23:04] [Repartidor-0] Pedido-1 entregado.
...
[Gestor] Todos los procesos finalizados.
🔍 Detalles de Implementación
Java - Pipeline de Procesos
Cliente (stdout) ──pipe──> Cocinero (stdin/stdout) ──pipe──> Repartidor (stdin)
Características:

Comunicación unidireccional mediante pipes del sistema operativo
Señal FIN para terminar el pipeline ordenadamente
Salida de logs a stderr para evitar interferencias
Gestión de errores con try-catch en cada proceso

Python - Colas Multiproceso
                    ┌──> Cocinero-1 ──┐
Cliente ──Queue──> ├──> Cocinero-2 ──┼──Queue──> Repartidor-1
                    └──> Cocinero-N ──┘          Repartidor-2
Características:

Múltiples workers procesando en paralelo
Centinelas (None) para coordinar la terminación
Lock de impresión para evitar salidas mezcladas
Distribución automática de centinelas entre workers

📊 Comparación de Implementaciones
CaracterísticaJavaPythonComunicaciónPipes (stdin/stdout)Colas (Queue)Escalabilidad1:1:1 (fijo)N:M:K (configurable)ComplejidadBaja (pipeline simple)Media (sincronización)ParalelismoSecuencialParalelo realUso educativoIPC básicoConcurrencia avanzada
🛠️ Estructura del Proyecto
restaurant-simulation/
├── Cliente.java              # Generador de pedidos
├── Cocinero.java            # Procesador de pedidos
├── Repartidor.java          # Entregador de pedidos
├── GestorRestaurante.java   # Orquestador principal
├── cliente.py               # Cliente Python
├── cocinero.py              # Cocinero Python
├── repartidor.py            # Repartidor Python
├── gestor.py                # Gestor Python
└── README.md                # Este archivo
🎓 Conceptos Demostrados

Comunicación entre procesos (IPC)

Pipes en Java
Colas multiproceso en Python


Patrones de concurrencia

Pipeline (Java)
Productor-Consumidor (Python)


Sincronización

Señales de terminación
Locks de impresión
Gestión de centinelas


Gestión de procesos

ProcessBuilder en Java
multiprocessing.Process en Python


🐛 Troubleshooting

Java
Error: "Cannot run program java"
bash# Verifica que Java esté en el PATH
java -version

Error de encoding en Windows
bash# Compila con UTF-8
javac -encoding UTF-8 *.java

Python
Error: "Queue is full"
Incrementa el tamaño de la cola o reduce el número de pedidos
Procesos zombie en Linux
El gestor espera a todos con .join() - no debería ocurrir
