# 💱 Conversor de Monedas - Challenge ONE

## 📋 Descripción del Proyecto

Aplicación de consola desarrollada en Java que permite convertir entre diferentes monedas del mundo utilizando tasas de cambio en tiempo real obtenidas a través de una API REST. El sistema incluye un **historial completo de conversiones** guardado en formato JSON con marca de tiempo.

Este proyecto fue desarrollado como parte del **Challenge de Backend** del programa **ONE (Oracle Next Education)** de Alura Latam, con el objetivo de poner en práctica conocimientos de:
- Consumo de APIs REST
- Manipulación y persistencia de datos JSON
- Programación Orientada a Objetos
- Manejo de excepciones
- Trabajo con fechas y tiempo (java.time)
- Interacción con el usuario

---

## ✨ Funcionalidades Implementadas

### ⚡ Conversiones Rápidas Predefinidas:
- **USD ↔ Peso Chileno (CLP)**
- **USD ↔ Real Brasileño (BRL)**
- **USD ↔ Peso Colombiano (COP)**

### 🌍 Conversión Libre:
- Permite convertir entre **cualquier par de más de 160 monedas** disponibles en la API
- Incluye monedas de África, América, Europa, Asia, Oceanía y Oriente Medio
- Ejemplos: KES (Chelín Keniano), XOF (Franco CFA), EUR (Euro), JPY (Yen), etc.
- Opción de ver catálogo antes de convertir
- Sistema de cancelación (escribir '0' para volver)

### 📋 Catálogo de Monedas:
- Lista organizada por continentes
- Más de 40 monedas comunes con sus nombres completos
- Facilita la búsqueda de códigos de moneda

### 📊 Historial de Conversiones:
- **Guarda automáticamente** cada conversión realizada
- **Marca de tiempo** precisa usando LocalDateTime
- **Persistencia** en archivo JSON con formato legible (pretty print)
- **Visualización** del historial completo con detalles de cada conversión
- Formato: fecha/hora, monedas, valores, tasa de cambio

### 🛡️ Validaciones y Manejo de Errores:
- Validación de valores numéricos positivos
- Verificación de códigos de moneda (3 letras)
- Manejo de errores de conexión
- Mensajes descriptivos para el usuario
- Recuperación automática de archivos corruptos
- Manejo robusto de excepciones con logging

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **Java** | 17 | Lenguaje principal |
| **Gson** | 2.10.1 | Parseo y serialización de JSON |
| **HttpClient** | java.net.http | Peticiones HTTP |
| **java.time** | - | Manejo de fechas y timestamps |
| **ExchangeRate API** | v6 | Obtención de tasas de cambio |
| **IntelliJ IDEA** | - | IDE de desarrollo |

---

## 📂 Estructura del Proyecto

```
ConversorMonedas/
│
├── src/
│   ├── ConversorMonedasMejorado.java
│   │   └── Clase principal
│   │       ├── Menú interactivo
│   │       ├── Lógica de conversiones
│   │       ├── Validaciones de entrada
│   │       └── Gestión del flujo del programa
│   │
│   ├── ConsultaMoneda.java
│   │   └── Cliente HTTP
│   │       ├── Peticiones a la API REST
│   │       ├── Parseo de respuestas JSON
│   │       └── Manejo de errores de red
│   │
│   ├── Moneda.java
│   │   └── Record (Java 14+)
│   │       └── Almacena datos de conversión
│   │           (monedaBase, monedaDestino, tasa)
│   │
│   └── HistorialConversiones.java
│       └── Gestión de persistencia
│           ├── Guardado en JSON
│           ├── Lectura del historial
│           ├── TypeAdapter para LocalDateTime
│           └── Visualización formateada
│
├── lib/
│   └── gson-2.10.1.jar
│       └── Librería para manipulación de JSON
│
├── historial_conversiones.json
│   └── Archivo generado automáticamente
│       └── Almacena todas las conversiones
│
└── README.md
    └── Documentación del proyecto
```

---

## 🚀 Instalación y Ejecución

### Requisitos Previos:

- ✅ **Java JDK 17 o superior** instalado
- ✅ **IntelliJ IDEA** (o cualquier IDE de Java)
- ✅ **Conexión a Internet** activa

### Pasos de Instalación:

#### 1. Clonar o Descargar el Proyecto

```bash
git clone https://github.com/tu-usuario/conversor-monedas.git
cd conversor-monedas
```

#### 2. Agregar la Librería Gson

**Opción A - Descarga manual:**
1. Descargar `gson-2.10.1.jar` desde [Maven Repository](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/)
2. Copiar el JAR a la carpeta `lib/` del proyecto
3. En IntelliJ: Click derecho en el JAR → **"Add as Library"**

**Opción B - Si usas Maven:**
```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

#### 3. Ejecutar el Programa

1. Abrir `ConversorMonedasMejorado.java` en IntelliJ
2. Click derecho en el archivo → **Run 'ConversorMonedasMejorado.main()'**
3. La aplicación se ejecutará en la consola

---

## 💡 Guía de Uso

### Menú Principal

Al ejecutar el programa verás:

```
****************************************************
*** Bienvenido al Conversor de Monedas del Cholo ***
****************************************************

++++++++++++++++++++++++
CONVERSIONES RÁPIDAS:
++++++++++++++++++++++++
1) Dólar (USD) => Peso Chileno (CLP)
2) Peso Chileno (CLP) => Dólar (USD)
3) Dólar (USD) => Real Brasileño (BRL)
4) Real Brasileño (BRL) => Dólar (USD)
5) Dólar (USD) => Peso Colombiano (COP)
6) Peso Colombiano (COP) => Dólar (USD)
___________________
CONVERSIÓN LIBRE:
-------------------
8) Convertir entre cualquier moneda
9) Ver lista de monedas disponibles
___________________
HISTORIAL:
-------------------
10) Ver historial de conversiones

PARA TERMINAR
-> 7) Salir <-
```

### Ejemplo 1: Conversión Rápida

```
Opción: 1
Ingrese el valor que desea convertir: 100

⏳ Consultando tasa de cambio...

====================================================================================
==  ✓ El valor 100,00 [USD] corresponde al valor final de =>>> 97850,00 [CLP]
====================================================================================

✅ Conversión guardada en historial
```

### Ejemplo 2: Conversión Libre

```
Opción: 8

¿Desea ver la lista de monedas disponibles Primero?
Ingrese 's' para ver lista o presione Enter para continuar: s

[SE MUESTRA EL CATÁLOGO COMPLETO DE MONEDAS]

>>> Presione Enter para continuar...

=== INGRESE LOS CÓDIGOS DE MONEDA ===
Moneda origen (3 letras) o '0' para volver: KES
Moneda destino (3 letras) o '0' para volver: XOF
Valor a convertir (o '0' para volver): 10000

⏳ Consultando tasa de cambio...

╔═══════════════════════════════════════════════╗
║           ✓ CONVERSIÓN EXITOSA                ║
╚═══════════════════════════════════════════════╝

  10000,00 [KES]  =  46875,25 [XOF]
  
  Tasa: 1 KES = 4,687525 XOF

═══════════════════════════════════════════════

✅ Conversión guardada en historial
```

### Ejemplo 3: Ver Historial

```
Opción: 10

╔════════════════════════════════════════════════════════════════╗
║              HISTORIAL DE CONVERSIONES                        ║
╚════════════════════════════════════════════════════════════════╝

📌 Conversión #1
   Fecha: 28/02/2026 15:30:45
   100,00 USD → 97850,00 CLP
   Tasa: 1 USD = 978,500000 CLP
   ─────────────────────────────────────────────────

📌 Conversión #2
   Fecha: 28/02/2026 15:35:12
   10000,00 KES → 46875,25 XOF
   Tasa: 1 KES = 4,687525 XOF
   ─────────────────────────────────────────────────

Total de conversiones: 2
```

---

## 🔍 Funcionamiento Técnico

### Flujo de la Aplicación

```
Usuario ingresa opción
        ↓
Validación de entrada
        ↓
Construcción de URL de API
        ↓
Petición HTTP GET
        ↓
Respuesta JSON de la API
        ↓
Parseo con Gson
        ↓
Extracción de tasa de cambio
        ↓
Cálculo: valor × tasa
        ↓
Mostrar resultado al usuario
        ↓
Guardar en historial JSON
```

### Estructura del JSON de la API

**Endpoint:** `https://open.er-api.com/v6/latest/{moneda_origen}`

**Respuesta:**
```json
{
  "result": "success",
  "base_code": "USD",
  "rates": {
    "CLP": 978.50,
    "BRL": 4.95,
    "COP": 3697.92,
    "KES": 129.50,
    "XOF": 607.45,
    ...
  }
}
```

### Estructura del Historial JSON

**Archivo:** `historial_conversiones.json`

```json
[
  {
    "monedaOrigen": "USD",
    "monedaDestino": "CLP",
    "valorOrigen": 100.0,
    "valorDestino": 97850.0,
    "tasa": 978.5,
    "fechaHora": "2026-02-28T15:30:45.123456789"
  },
  {
    "monedaOrigen": "KES",
    "monedaDestino": "XOF",
    "valorOrigen": 10000.0,
    "valorDestino": 46875.25,
    "tasa": 4.687525,
    "fechaHora": "2026-02-28T15:35:12.987654321"
  }
]
```

### Arquitectura del Código

**Patrones Utilizados:** 
- **Separación de Responsabilidades (SoC)**
- **Data Access Object (DAO)**
- **Data Transfer Object (DTO)**

**Componentes:**
- **ConversorMonedasMejorado:** Capa de presentación (UI/Menú)
- **ConsultaMoneda:** Capa de acceso a datos externos (API)
- **HistorialConversiones:** Capa de persistencia (JSON)
- **Moneda:** Modelo de datos inmutable (Record)

---

## 🧪 Casos de Prueba

Durante el desarrollo se realizaron pruebas exhaustivas:

| Escenario | Entrada | Resultado Esperado | Estado |
|-----------|---------|-------------------|--------|
| Conversión rápida USD→CLP | 100 USD | ~97,850 CLP + guardado | ✅ Aprobado |
| Conversión libre KES→XOF | 10,000 KES | ~46,875 XOF + guardado | ✅ Aprobado |
| Ver historial vacío | - | Mensaje "historial vacío" | ✅ Aprobado |
| Ver historial con datos | - | Lista formateada | ✅ Aprobado |
| Valor negativo | -100 | Error: "Valor debe ser mayor que cero" | ✅ Aprobado |
| Código inválido | XX | Error: "Código debe tener 3 letras" | ✅ Aprobado |
| Monedas iguales | USD→USD | Error: "Seleccione monedas diferentes" | ✅ Aprobado |
| Sin conexión | Cualquiera | Error: "Error de conexión" | ✅ Aprobado |
| Entrada no numérica | "abc" | Error: "Entrada inválida" | ✅ Aprobado |
| Cancelación (0) | 0 | Vuelve al menú | ✅ Aprobado |
| Archivo historial corrupto | - | Crea archivo nuevo | ✅ Aprobado |
| Persistencia entre sesiones | - | Historial se mantiene | ✅ Aprobado |

---

## 🎯 Conceptos Aplicados

Este proyecto demuestra dominio de:

### Java Core:
- ✅ Clases y Objetos
- ✅ Records (Java 14+)
- ✅ Manejo de Excepciones (try-catch, tipos específicos)
- ✅ Entrada/Salida con Scanner
- ✅ File I/O (FileReader, FileWriter)
- ✅ Control de flujo (while, switch, if-else)
- ✅ Strings y formateo (printf, String.format)

### APIs y Redes:
- ✅ Consumo de API REST
- ✅ Peticiones HTTP GET
- ✅ Manejo de respuestas HTTP
- ✅ Códigos de estado

### JSON y Persistencia:
- ✅ Parseo de JSON con Gson
- ✅ Serialización de objetos a JSON
- ✅ TypeAdapters personalizados (LocalDateTime)
- ✅ Pretty printing
- ✅ Manejo de archivos JSON

### Fechas y Tiempo:
- ✅ LocalDateTime
- ✅ DateTimeFormatter
- ✅ Timestamps precisos
- ✅ Formateo de fechas

### Buenas Prácticas:
- ✅ Código modular y reutilizable
- ✅ Validación de entrada del usuario
- ✅ Manejo robusto de errores
- ✅ Logging de operaciones
- ✅ Mensajes claros al usuario
- ✅ Nombres descriptivos de variables
- ✅ Comentarios JavaDoc
- ✅ Separación de responsabilidades

---

## 🌟 Características Destacadas

### Más Allá de los Requisitos:

1. **📊 Persistencia Completa:** Sistema de historial con marca de tiempo
2. **📋 Catálogo Extenso:** No solo 6 conversiones, acceso a 160+ monedas
3. **🌍 Organización Geográfica:** Lista ordenada por continentes
4. **🔙 Navegación Intuitiva:** Opción de cancelar con '0' en cualquier momento
5. **✅ Validaciones Exhaustivas:** Previene todos los casos de error comunes
6. **💬 UX Mejorada:** Mensajes claros, emojis Unicode, formato legible
7. **🎨 Interfaz Profesional:** Bordes, tablas y formato estructurado
8. **💾 Recuperación de Errores:** Manejo automático de archivos corruptos
9. **🔄 TypeAdapters Custom:** Serialización correcta de LocalDateTime

---

## 📚 Recursos de Aprendizaje

### Durante el desarrollo se consultaron:

- Formación ¡Con certificado! Java Orientado a Objetos G9 - ONE (https://app.aluracursos.com/formacion-java-grupo9-one)
- [Documentación oficial de Java](https://docs.oracle.com/en/java/)
- [Guía de Gson](https://github.com/google/gson/blob/master/UserGuide.md)
- [ExchangeRate API Docs](https://www.exchangerate-api.com/docs/overview)
- [Java HttpClient Tutorial](https://www.baeldung.com/java-9-http-client)
- [Java Records Guide](https://www.baeldung.com/java-record-keyword)
- [java.time Package](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/package-summary.html)
- [Gson TypeAdapter](https://www.baeldung.com/gson-custom-serialization)

---

## 🚧 Posibles Mejoras Futuras

Ideas para expandir el proyecto:

- [ ] 📈 **Gráficos de tendencias** de tasas históricas
- [ ] 🔔 **Sistema de alertas** para tasas objetivo
- [ ] 🌐 **Interfaz gráfica** con JavaFX
- [ ] 🔄 **Caché de tasas** para modo offline
- [ ] 📱 **Versión móvil** para Android
- [ ] 🌍 **Internacionalización** (i18n) multi-idioma
- [ ] 📊 **Exportar historial** a CSV/Excel
- [ ] 🔍 **Búsqueda y filtrado** del historial
- [ ] 📧 **Envío de reportes** por email
- [ ] 🗄️ **Base de datos** en lugar de JSON

---

## ⚙️ Información de la API

### ExchangeRate API - Plan Gratuito

| Característica | Detalle |
|---------------|---------|
| **URL Base** | https://open.er-api.com/v6/latest/ |
| **Peticiones/mes** | 1,500 (gratuitas) |
| **Actualización** | Cada 24 horas |
| **Monedas soportadas** | 161+ |
| **Autenticación** | No requiere API Key |
| **Uptime** | 99.9% |

---

## 🐛 Solución de Problemas

### Error: "Cannot resolve symbol 'Gson'"
**Causa:** Librería Gson no agregada correctamente  
**Solución:** Click derecho en gson-2.10.1.jar → Add as Library

### Error: "Error de conexión"
**Causa:** Sin acceso a Internet  
**Solución:** Verificar conexión y firewall

### Error: "EOFException"
**Causa:** Archivo historial_conversiones.json corrupto  
**Solución:** Borrar el archivo, se creará uno nuevo automáticamente

### Error: "Failed making field accessible"
**Causa:** Gson sin TypeAdapter para LocalDateTime  
**Solución:** Usar la versión actualizada de HistorialConversiones.java

---

## 👨‍💻 Autor
- Alejandro A. Landa Melo
**Desarrollado como parte de:**  
Challenge Backend - ONE (Oracle Next Education) + Alura Latam

**Programa:** Oracle Next Education (ONE)  
**Fecha:** Febrero 2026

---

## 📄 Licencia

Este proyecto fue desarrollado con fines educativos como parte del programa ONE de Oracle y Alura Latam. Puede ser descargado y utilizado,
siempre que nombres la Fuente.

---

## 🙏 Agradecimientos

- **Oracle** y **Alura Latam** por la oportunidad de aprendizaje
- **Instructores** del programa ONE por su guía y soporte
- **Comunidad de desarrolladores** por recursos y soluciones compartidas
- **ExchangeRate API** por proporcionar un servicio gratuito y confiable

---

## 📞 Información de Contacto

Para consultas sobre este proyecto:

- **GitHub:** https://github.com/alejolanda
- **LinkedIn:** https://www.linkedin.com/in/alejandro-landa-ing-informatico/
- **Email:** pleiades.solution.go@gmail.com -- alelanda@gmail.com

---

**⭐ Si este proyecto te fue útil como referencia, considera darle una estrella en GitHub**

---

## 📊 Estadísticas del Proyecto

- **Líneas de código:** ~700+
- **Clases:** 4
- **Métodos:** 15+
- **Funcionalidades:** 10
- **Tiempo de desarrollo:** Challenge ONE G-9 2025-2026 (https://app.aluracursos.com/formacion-java-grupo9-one)

---

*Proyecto desarrollado con dedicación como parte del Challenge ONE - Backend Development 2026*
