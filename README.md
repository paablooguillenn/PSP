🖨️ Sistema de Gestión de Impresora

Sistema de simulación que demuestra exclusión mutua y sincronización de procesos.
Múltiples usuarios compiten por acceder a una única impresora compartida, utilizando semáforos para coordinar el acceso sin conflictos.

🧩 Descripción

El sistema simula el problema clásico de recursos compartidos, donde varios usuarios envían trabajos de impresión y deben coordinarse para acceder a la impresora sin interferencias.

Usuario: Genera trabajos y solicita acceso a la impresora.

Impresora: Recurso compartido que procesa un trabajo a la vez.

Gestor: Orquesta la simulación y controla los accesos.

🏗️ Arquitecturas
🟣 Java — Cliente-Servidor con Sockets
Usuarios ──(Socket TCP)──▶ Servidor Impresora (Semáforo interno)


Comunicación por red (puerto 5000)

Protocolo: PRINT:id:trabajo → DONE

Servidor secuencial con semáforo

🟠 Python — Multiprocesamiento con Semáforo Compartido
Gestor ──(Semaphore)──▶ Usuarios (Processes) ──▶ Impresora


Semáforo binario compartido entre procesos

Sin comunicación por red

Garantía de liberación con try-finally

⚙️ Requisitos

Java: JDK 8+

Python: 3.6+ (con multiprocessing)

🧪 Compilación y Ejecución
🔹 Java

Compilación

javac GestorImpresion.java Impresora.java Usuario.java


Configuración (GestorImpresion.java)

private static final int NUM_USUARIOS = 3;
private static final int TRABAJOS_POR_USUARIO = 2;


Ejecución

java GestorImpresion


Ejemplo de salida:

[Impresora] Usuario-1 - Iniciando Trabajo-1
[Usuario-2] Esperando para imprimir Trabajo-1...
[Impresora] Usuario-1 - Trabajo-1 completado
[Usuario-2] Accediendo a la impresora para imprimir Trabajo-1...
Todos los trabajos han sido impresos.

🔸 Python

Configuración (gestorimpresion.py)

pythonGestor = GestorImpresion(num_usuarios=4, trabajos_por_usuario=3)


Ejecución

python gestorimpresion.py

🔍 Comparación de Implementaciones
Característica	Java	Python
Arquitectura	Cliente-Servidor (Sockets TCP)	Multiprocesamiento
Comunicación	Red (Protocolo personalizado)	Memoria compartida
Sincronización	Semáforo en servidor	Semáforo compartido
Compatibilidad	Alta (red real)	Local (misma máquina)
Overhead	Alto (red)	Bajo (memoria)
📘 Conceptos Demostrados

🔒 Exclusión mutua con semáforos

🔁 Sincronización de procesos

🌐 Cliente-Servidor (Java)

🧠 Multiprocesamiento (Python)

⚠️ Race conditions: cómo evitarlas

🧱 Estructura del Proyecto
├── GestorImpresion.java   # Orquestador Java
├── Impresora.java          # Servidor con semáforo
├── Usuario.java            # Cliente
├── gestorimpresion.py      # Orquestador Python
├── impresora.py            # Recurso compartido
├── usuario.py              # Proceso con semáforo
├── README.md
└── LICENSE (MIT)

🪪 Licencia

Este proyecto está licenciado bajo la MIT License.
