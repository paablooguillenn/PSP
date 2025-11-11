🍽️ PSP-DAM-ACTEVA03B — Simulación de Cocina Concurrente

Proyecto que simula el funcionamiento de una cocina con múltiples cocineros y pedidos, demostrando el uso de procesos e hilos concurrentes y la sincronización con semáforos o bloqueos.

El objetivo es evitar conflictos al acceder a recursos compartidos (como la cocina o la lista de pedidos), representando el clásico problema de exclusión mutua.

🧩 Descripción del Proyecto

Cada cocinero representa un hilo o proceso que toma pedidos de una cola compartida (la cocina), los procesa y registra su actividad en un log de pedidos.

La simulación está implementada en dos versiones:

🟣 Java — Utiliza hilos y sincronización con monitores o semáforos.

🟠 Python — Utiliza el módulo multiprocessing o threading con bloqueos.

⚙️ Funcionalidades Principales

Creación y gestión de múltiples pedidos.

Procesamiento concurrente por parte de varios cocineros.

Sincronización del acceso a la cocina (recurso compartido).

Registro de pedidos procesados en un archivo de log.

Ejecución controlada para evitar race conditions.

🏗️ Estructura del Proyecto
PSPDAM-ACTEV03/
├── PSPDAM-ACTEV03-Java/
│   └── PSP-DAM-ACTEVA03B/
│       └── src/
│           ├── Cocina.java        # Clase principal: gestiona pedidos y cocineros
│           ├── Cocinero.java      # Hilo de ejecución para cada cocinero
│           ├── Pedido.java        # Representa un pedido individual
│           └── log_pedidos.txt    # Registro de pedidos procesados
│
└── PSPDAM-ACTEV03-Python/
    └── PSP-DAM-ACTEVA03B/
        ├── cocina.py              # Gestor principal: controla los procesos
        ├── cocinero.py            # Proceso o hilo que prepara pedidos
        └── pedido.py              # Representa un pedido

🚀 Ejecución
🟣 Java

Compilar

cd PSPDAM-ACTEV03/PSPDAM-ACTEV03-Java/PSP-DAM-ACTEVA03B/src
javac *.java


Ejecutar

java Cocina


📄 Los resultados se registrarán en el archivo log_pedidos.txt.

🟠 Python

Ejecutar

cd PSPDAM-ACTEV03/PSPDAM-ACTEV03-Python/PSP-DAM-ACTEVA03B
python cocina.py


📄 El programa mostrará en consola el progreso de los cocineros y pedidos.

🧠 Conceptos Demostrados

🔒 Exclusión mutua

🧵 Programación concurrente y paralela

🕹️ Sincronización con semáforos o bloqueos

⚠️ Prevención de race conditions

📋 Comunicación entre hilos/procesos

📘 Requisitos
Lenguaje	Versión mínima	Librerías necesarias
Java	JDK 8+	Ninguna externa
Python	3.6+	multiprocessing o threading (estándar)
🧾 Licencia

Este proyecto se distribuye bajo la MIT License.
Puedes usarlo, modificarlo y compartirlo libremente, siempre citando la fuente.
