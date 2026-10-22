# TallerPoo_CarlosV

Talleres prácticos de Programación Orientada a Objetos (POO) en Java. El proyecto tiene dos partes que se fueron construyendo en el mismo repositorio: la primera trabaja clases, objetos, constructores y depuración (`com.taller.poo`), y la segunda es un juego de consola, la Arena de Héroes RPG, que aplica encapsulamiento, herencia, colecciones, genéricos y relaciones entre clases (`co.edu.puj.rpg`).

**Autor:** Carlos Mario Velásquez
**Curso:** M4

**Guías:**
1. _Programación Orientada a Objetos (POO), Clases y Objetos en Java con IntelliJ IDEA_
2. _Encapsulamiento y Modificadores de Acceso en Java_
3. _Arena RPG con POO Avanzada, Colecciones y Genéricos_
4. _Relaciones entre Clases en Java_

## Requisitos

- JDK 17 o 21 (LTS)
- IntelliJ IDEA Community Edition
- Maven (incluido en IntelliJ)

## Estructura del proyecto

```
src/main/java
├── com.taller.poo
│   ├── seccion1  → Vehiculo, Estudiante, CuentaBancaria
│   ├── seccion2  → ConversorMoneda
│   ├── seccion3  → (sin clases nuevas: los constructores están en seccion1)
│   ├── seccion4  → Principal
│   ├── seccion5  → SistemaGestionCuentas
│   └── seccion6  → PruebaEstudiante, GestorVehiculos, CuentaBancaria, Vehiculo, PruebaCasos3y4
│
└── co.edu.puj.rpg
    ├── modelo
    │   ├── ICombatiente, Personaje (abstracta), Guerrero, Mago, Pocion
    │   ├── Arma, CorazonRPG, Gremio, Escuadron
    │   ├── enemigo/Enemigo       (de la guía 2, ya no se usa en la batalla)
    │   ├── inventario/Inventario (clase genérica)
    │   └── tablero/Tablero
    └── principal → Main, AplicacionArenaRPG
```

---

# Parte 1: Clases y objetos (`com.taller.poo`)

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

---

# Parte 2: Arena de Héroes RPG (`co.edu.puj.rpg`)

Juego de consola hecho por etapas, una por guía.

## Qué se hizo en cada guía

**Guía 2: encapsulamiento.** Atributos privados, getters y setters con validación (la vida no baja de 0 ni pasa del máximo, el oro y el nivel no aceptan valores inválidos). Primera batalla en consola con `do-while`, `switch`, `printf` y `StringBuilder`.

**Guía 3: herencia, colecciones y genéricos.**
- `Personaje` pasó a ser una clase abstracta que implementa la interfaz `ICombatiente`.
- `Guerrero` y `Mago` heredan de `Personaje` y sobrescriben `realizarAtaqueEspecial()`. También tienen sobrecarga de `atacar()`.
- Los héroes y los enemigos se guardan en `ArrayList<Personaje>`. Los caídos se quitan con `removeIf`.
- `Inventario<T>` es un contenedor genérico, usado como `Inventario<Pocion>`.
- `Tablero` es una matriz de `char` de 5x5.

**Guía 4: relaciones entre clases.**

| Relación | Clases | Cómo está hecha |
|---|---|---|
| Asociación unidireccional | `Personaje` -> `Arma` | El personaje guarda el arma (puede ser `null`). El arma no conoce al personaje. |
| Asociación bidireccional | `Personaje` <-> `Gremio` | Ambos lados se actualizan entre sí. Si el personaje cambia de gremio, sale del anterior. |
| Agregación | `Escuadron` -> `Personaje` | El escuadrón solo guarda referencias. Si se expulsa a alguien, el personaje sigue existiendo. |
| Composición | `Personaje` -> `CorazonRPG` | El corazón se crea dentro del constructor y no se puede reemplazar desde afuera. |

## Cómo se juega la batalla (`Main`)

Héroes: Thorin (Guerrero, con Espada +10) y Gandalf (Mago, con Bastón rúnico +5).
Enemigos: Orco, Brujo y Troll.

En cada ronda se dibuja el tablero y cada héroe elige una acción:

1. Atacar: se escoge un enemigo como objetivo.
2. Usar inventario: gasta una poción y cura vida.
3. Mover en el tablero: arriba, abajo, izquierda o derecha. No deja salir del mapa ni pisar una casilla ocupada.

Después atacan los enemigos. Se pierde si todo el escuadrón héroe cae. Se gana si mueren todos los enemigos o si Thorin llega al cofre (`C`, casilla del centro).

Símbolos del tablero: `G` guerrero, `M` mago, `E` enemigo, `C` cofre, `.` casilla vacía.

## Menú de `AplicacionArenaRPG`

```
1. Crear personaje (nombre, clase y nivel)
2. Crear gremio (permite inscribir personajes al crearlo)
3. Reclutar o expulsar personajes de un escuadrón
4. Equipar o retirar un arma
5. Mostrar el estado detallado
6. Salir
```

## Notas de la Parte 2

- Con el arma equipada, el daño en combate es `ataqueBase` + daño del arma (`getAtaqueEfectivo()`). El método `calcularAtaqueTotal()` (nivel x 5 + arma) es el que pide la guía 4 y solo se muestra en el menú.
- `toString()` de `Gremio` solo imprime los nombres de los miembros, para evitar un `StackOverflowError` por llamadas circulares con `Personaje`.

---

# Cómo ejecutar

1. Abre el proyecto en IntelliJ IDEA.
2. Abre la clase que quieras probar:
    - Parte 1: `Principal`, `SistemaGestionCuentas`, etc.
    - Parte 2: `Main` (la batalla) o `AplicacionArenaRPG` (el menú).
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
- **Entrar a un método (Step Into):** `F7`.
- **Evaluar una expresión:** `Alt + F8`.