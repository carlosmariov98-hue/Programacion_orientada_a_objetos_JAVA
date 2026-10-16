# TallerPoo_CarlosV

Taller práctico de Programación Orientada a Objetos (POO) en Java: clases, objetos, constructores, métodos de instancia y estáticos, y depuración con IntelliJ IDEA.

**Autor:** Carlos Mario Velásquez
**Curso:** M4
**Guía:** _Programación Orientada a Objetos (POO), Clases y Objetos en Java con IntelliJ IDEA_

## Requisitos

- JDK 17 o 21 (LTS)
- IntelliJ IDEA Community Edition
- Maven (incluido en IntelliJ)

## Estructura del proyecto

```
com.taller.poo
├── seccion1  → Vehiculo, Estudiante, CuentaBancaria
├── seccion2  → ConversorMoneda
├── seccion3  → (sin clases nuevas: los constructores están en seccion1)
├── seccion4  → Principal
├── seccion5  → SistemaGestionCuentas
└── seccion6  → PruebaEstudiante, GestorVehiculos, CuentaBancaria, Vehiculo, PruebaCasos3y4
```

## Contenido por sección

| Sección | Tema | Clases |
|---|---|---|
| 1 | Modelado de clases y atributos | `Vehiculo`, `Estudiante`, `CuentaBancaria` |
| 2 | Métodos de instancia y estáticos | `obtenerReporteAcademicos()`, `depositar()`, `retirar()` y `ConversorMoneda` |
| 3 | Constructores y `this` | Constructores sobrecargados de `Vehiculo` (2) y `CuentaBancaria` (3) |
| 4 | Instanciación de objetos | `Principal` (3 vehículos, 3 estudiantes y 3 cuentas) |
| 5 | Reto integrador | `SistemaGestionCuentas` (menú con `do-while` y `switch`) |
| 6 | Depuración | 4 casos de estudio corregidos |

## Descripción de las clases principales

| Clase | Descripción |
|---|---|
| `Vehiculo` | Marca, modelo, kilometraje, velocidad máxima y estado del motor |
| `Estudiante` | Identificación, nombre, promedio y estado de matrícula; genera un reporte con `StringBuilder` |
| `CuentaBancaria` | Número, titular, saldo y límite de crédito (`Double`); permite depositar y retirar |
| `ConversorMoneda` | Clase utilitaria con métodos estáticos (convertir dólares a euros y dar formato de moneda) |

## Casos de depuración (Sección 6)

| Caso | Error | Corrección |
|---|---|---|
| 1 | `NullPointerException` por variable sin instanciar | Crear el objeto con `new` |
| 2 | Llamar un método de instancia desde `main` (estático) | Crear una instancia o declarar el método `static` |
| 3 | Constructor declarado con `void` | Quitar el tipo de retorno |
| 4 | Sombreado de variables | Usar `this.` en las asignaciones |

## Cómo ejecutar

1. Abre el proyecto en IntelliJ IDEA.
2. Abre la clase que quieras probar (`Principal`, `SistemaGestionCuentas`, etc.).
3. Haz clic en el triángulo verde junto a `main`, o presiona `Shift + F10`.

Hay varios `main` en el proyecto, así que ejecuta siempre desde el archivo que quieres probar.

### Salida esperada de `Principal` (extracto)

```
=== REPORTE DE CUENTAS BANCARIAS ===
Cuenta: 1001 | Titular: Carlos Pérez | Saldo Final: $1250.75 | Crédito: $500.00
Cuenta: 1002 | Titular: Ana Gómez | Saldo Final: $50.00 | Crédito: $100.00
```

### Uso de `SistemaGestionCuentas`

Cuentas de prueba precargadas: `1001`, `1002` y `1003`.

```
1. Depositar en una cuenta
2. Retirar de una cuenta
3. Ver reporte consolidado
4. Salir
```

Los montos se escriben con **punto** decimal (por ejemplo `100.50`).

## Depuración en IntelliJ

- **Breakpoint:** clic en el margen izquierdo de la línea.
- **Ejecutar en modo Debug:** `Shift + F9`.
- **Avanzar paso a paso (Step Over):** `F8`.
- **Evaluar una expresión:** `Alt + F8`.

