🖨️ Sistema de Gestión de Impresora
Sistema de simulación que demuestra exclusión mutua y sincronización de procesos. Múltiples usuarios compiten por acceder a una única impresora compartida usando semáforos.
Descripción
Simula el problema clásico de recursos compartidos: varios usuarios envían trabajos de impresión y deben coordinarse para acceder a la impresora sin conflictos.

Usuario: Genera trabajos y solicita acceso a la impresora
Impresora: Recurso compartido que procesa un trabajo a la vez
Gestor: Orquesta la simulación

Arquitecturas
Java: Cliente-Servidor con Sockets
Usuarios ──(Socket TCP)──> Servidor Impresora (Semáforo interno)

Comunicación por red (puerto 5000)
Protocolo: PRINT:id:trabajo → DONE
Servidor secuencial con semáforo

Python: Multiprocesamiento con Semáforo Compartido
Gestor ──(Semaphore)──> Usuarios (Processes) ──> Impresora

Semáforo binario compartido entre procesos
Sin comunicación por red
Garantía try-finally para liberación

Requisitos

Java: JDK 8+
Python: 3.6+ (biblioteca estándar)

Uso
Java
bash# Compilar
javac GestorImpresion.java Impresora.java Usuario.java

# Ejecutar
java GestorImpresion
Configuración (editar GestorImpresion.java):
javaprivate static final int NUM_USUARIOS = 3;
private static final int TRABAJOS_POR_USUARIO = 2;
Python
bash# Ejecutar
python gestorimpresion.py
Configuración (editar gestorimpresion.py):
pythongestor = GestorImpresion(num_usuarios=4, trabajos_por_usuario=3)
Salida Ejemplo
[Usuario-1] Accediendo a la impresora para imprimir Trabajo-1
[Impresora] Usuario-1 - Iniciando Trabajo-1
[Usuario-2] Esperando para imprimir Trabajo-1...
[Impresora] Usuario-1 - Trabajo-1 completado
[Usuario-2] Accediendo a la impresora para imprimir Trabajo-1
...
Todos los trabajos han sido impresos.
Comparación
CaracterísticaJavaPythonComunicaciónSockets TCPMemoria compartidaSincronizaciónSemáforo en servidorSemáforo compartidoComplejidadAlta (red + protocolo)Baja (API directa)Distribuible✅ Sí (red real)❌ Solo misma máquinaOverheadAlto (red)Bajo (memoria)
Conceptos Demostrados

🔒 Exclusión mutua con semáforos
🔄 Sincronización de procesos
🌐 Cliente-Servidor (Java)
📦 Multiprocesamiento (Python)
🛡️ Race conditions: cómo evitarlas

Estructura
├── GestorImpresion.java     # Orquestador Java
├── Impresora.java            # Servidor con semáforo
├── Usuario.java              # Cliente
├── gestorimpresion.py        # Orquestador Python
├── impresora.py              # Recurso compartido
├── usuario.py                # Proceso con semáforo
└── README.md
Licencia
MIT License
