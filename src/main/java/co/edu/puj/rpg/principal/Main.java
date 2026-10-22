package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Arma;
import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.Pocion;
import co.edu.puj.rpg.modelo.inventario.Inventario;
import co.edu.puj.rpg.modelo.tablero.Tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Random azar = new Random();

    public static void main(String[] args) {
        Tablero tablero = new Tablero(2, 2);
        List<Personaje> heroes = new ArrayList<>();
        List<Personaje> enemigos = new ArrayList<>();
        Inventario<Pocion> inventario = new Inventario<>();

        heroes.add(new Guerrero("Thorin", 120, 15, 0, 0, 10));
        heroes.add(new Mago("Gandalf", 80, 25, 0, 1, 50));

        enemigos.add(new Guerrero("Orco", 70, 12, 4, 0, 8));
        enemigos.add(new Mago("Brujo", 50, 18, 0, 4, 40));
        enemigos.add(new Guerrero("Troll", 90, 14, 4, 4, 12));

        // Armas de los héroes: suman daño a sus ataques
        heroes.get(0).equiparArma(new Arma("Espada", 10));
        heroes.get(1).equiparArma(new Arma("Bastón rúnico", 5));

        inventario.agregar(new Pocion("Poción pequeña", 25));
        inventario.agregar(new Pocion("Poción pequeña", 25));
        inventario.agregar(new Pocion("Poción grande", 50));

        Personaje principal = heroes.get(0);
        boolean cofreAlcanzado = false;
        int ronda = 0;

        System.out.println("¡Comienza la batalla en la arena!");

        do {
            ronda++;
            System.out.printf("%n========== RONDA %d ==========%n", ronda);
            redibujar(tablero, heroes, enemigos);
            mostrarEstado(heroes, enemigos, inventario);

            // ---- Turno del jugador ----
            for (Personaje heroe : heroes) {
                if (!heroe.estaVivo()) {
                    continue;
                }
                if (!hayVivos(enemigos) || cofreAlcanzado) {
                    break;
                }

                System.out.printf("%n--- Turno de %s ---%n", heroe.getNombre());
                boolean accionHecha = false;

                while (!accionHecha) {
                    System.out.println("1. Atacar");
                    System.out.println("2. Usar inventario");
                    System.out.println("3. Mover en tablero");
                    int opcion = leerEntero();

                    switch (opcion) {
                        case 1:
                            accionHecha = atacar(heroe, enemigos);
                            break;
                        case 2:
                            accionHecha = usarInventario(heroe, inventario);
                            break;
                        case 3:
                            accionHecha = mover(heroe, heroes, enemigos, tablero);
                            if (accionHecha && heroe == principal
                                    && tablero.esCofre(heroe.getPosicionX(), heroe.getPosicionY())) {
                                cofreAlcanzado = true;
                            }
                            break;
                        default:
                            System.out.println("Opción inválida, elige 1, 2 o 3.");
                    }
                }
                redibujar(tablero, heroes, enemigos);
            }

            // ---- Turno de los enemigos ----
            if (hayVivos(enemigos) && !cofreAlcanzado) {
                System.out.println("\n*** Turno de los enemigos ***");
                for (Personaje enemigo : enemigos) {
                    List<Personaje> objetivos = vivos(heroes);
                    if (objetivos.isEmpty()) {
                        break;
                    }
                    if (!enemigo.estaVivo()) {
                        continue; // sigue en la lista hasta la limpieza de bajas
                    }
                    Personaje objetivo = objetivos.get(azar.nextInt(objetivos.size()));

                    if (enemigo instanceof Mago mago && !mago.puedeLanzarHechizo()) {
                        mago.atacar(objetivo);
                    } else {
                        enemigo.realizarAtaqueEspecial(objetivo);
                    }
                    if (!objetivo.estaVivo()) {
                        System.out.printf("%s ha caído en combate.%n", objetivo.getNombre());
                    }
                }
            }

            // ---- Limpieza de bajas ----
            heroes.removeIf(p -> !p.estaVivo());
            enemigos.removeIf(p -> !p.estaVivo());

        } while (!heroes.isEmpty() && sumaVida(heroes) > 0 && !enemigos.isEmpty() && !cofreAlcanzado);

        System.out.println();
        redibujar(tablero, heroes, enemigos);
        if (cofreAlcanzado) {
            System.out.println("¡Victoria! Thorin llegó al cofre del tesoro.");
        } else if (enemigos.isEmpty()) {
            System.out.println("¡Victoria! Todos los enemigos fueron eliminados.");
        } else {
            System.out.println("Derrota... el escuadrón héroe fue aniquilado.");
        }
        sc.close();
    }

    // ---------- Acciones del jugador ----------

    private static boolean atacar(Personaje heroe, List<Personaje> enemigos) {
        List<Personaje> objetivos = vivos(enemigos);
        System.out.println("Elige el objetivo:");
        for (int i = 0; i < objetivos.size(); i++) {
            Personaje e = objetivos.get(i);
            System.out.printf("%d. %s (vida: %d)%n", i + 1, e.getNombre(), e.getVida());
        }
        int eleccion = leerEntero();
        if (eleccion < 1 || eleccion > objetivos.size()) {
            System.out.println("Objetivo inválido.");
            return false;
        }
        Personaje objetivo = objetivos.get(eleccion - 1);

        if (heroe instanceof Mago mago && !mago.puedeLanzarHechizo()) {
            System.out.println("Sin maná suficiente, se usa el bastón.");
            mago.atacar(objetivo);
        } else {
            heroe.realizarAtaqueEspecial(objetivo);
        }
        if (!objetivo.estaVivo()) {
            System.out.printf("%s ha sido derrotado.%n", objetivo.getNombre());
        }
        return true;
    }

    private static boolean usarInventario(Personaje heroe, Inventario<Pocion> inventario) {
        if (inventario.estaVacio()) {
            System.out.println("El inventario está vacío.");
            return false;
        }
        Pocion pocion = inventario.quitar(0);
        int vidaAntes = heroe.getVida();
        heroe.curar(pocion.getCantidadCura());
        System.out.printf("%s usa %s y recupera %d de vida. Quedan %d pociones.%n",
                heroe.getNombre(), pocion.getNombre(), heroe.getVida() - vidaAntes, inventario.cantidad());
        return true;
    }

    private static boolean mover(Personaje heroe, List<Personaje> heroes,
                                 List<Personaje> enemigos, Tablero tablero) {
        System.out.println("1. Arriba  2. Abajo  3. Izquierda  4. Derecha");
        int direccion = leerEntero();

        int nuevoX = heroe.getPosicionX();
        int nuevoY = heroe.getPosicionY();
        switch (direccion) {
            case 1: nuevoY--; break;
            case 2: nuevoY++; break;
            case 3: nuevoX--; break;
            case 4: nuevoX++; break;
            default:
                System.out.println("Dirección inválida.");
                return false;
        }

        if (!tablero.dentroDeLimites(nuevoX, nuevoY)) {
            System.out.println("No puedes salir del tablero.");
            return false;
        }
        if (celdaOcupada(nuevoX, nuevoY, heroes, enemigos)) {
            System.out.println("Esa casilla ya está ocupada.");
            return false;
        }
        heroe.setPosicion(nuevoX, nuevoY);
        System.out.printf("%s se mueve a (%d, %d).%n", heroe.getNombre(), nuevoX, nuevoY);
        return true;
    }

    // ---------- Utilidades ----------

    private static int leerEntero() {
        System.out.print("> ");
        if (sc.hasNextInt()) {
            return sc.nextInt();
        }
        sc.next(); // descarta el texto que no era número
        return -1;
    }

    private static List<Personaje> vivos(List<Personaje> lista) {
        List<Personaje> resultado = new ArrayList<>();
        for (Personaje p : lista) {
            if (p.estaVivo()) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    private static boolean hayVivos(List<Personaje> lista) {
        return !vivos(lista).isEmpty();
    }

    private static int sumaVida(List<Personaje> lista) {
        int total = 0;
        for (Personaje p : lista) {
            total += p.getVida();
        }
        return total;
    }

    private static boolean celdaOcupada(int x, int y, List<Personaje> heroes, List<Personaje> enemigos) {
        for (Personaje p : vivos(heroes)) {
            if (p.getPosicionX() == x && p.getPosicionY() == y) {
                return true;
            }
        }
        for (Personaje p : vivos(enemigos)) {
            if (p.getPosicionX() == x && p.getPosicionY() == y) {
                return true;
            }
        }
        return false;
    }

    private static void redibujar(Tablero tablero, List<Personaje> heroes, List<Personaje> enemigos) {
        tablero.actualizar(heroes, enemigos);
        System.out.println();
        tablero.mostrarTablero(tablero.getMatriz());
        System.out.println("G=Guerrero  M=Mago  E=Enemigo  C=Cofre");
    }

    private static void mostrarEstado(List<Personaje> heroes, List<Personaje> enemigos,
                                      Inventario<Pocion> inventario) {
        System.out.println("\nHéroes:");
        for (Personaje h : heroes) {
            String arma = (h.getArmaEquipada() != null) ? h.getArmaEquipada().getNombre() : "sin arma";
            if (h instanceof Mago mago) {
                System.out.printf("  %-8s Vida: %3d  Maná: %3d  Ataque: %2d (%s)%n",
                        h.getNombre(), h.getVida(), mago.getPuntosMana(), h.getAtaqueEfectivo(), arma);
            } else {
                System.out.printf("  %-8s Vida: %3d  Ataque: %2d (%s)%n",
                        h.getNombre(), h.getVida(), h.getAtaqueEfectivo(), arma);
            }
        }
        System.out.println("Enemigos:");
        for (Personaje e : enemigos) {
            if (e instanceof Mago mago) {
                System.out.printf("  %-8s Vida: %3d  Maná: %3d%n", e.getNombre(), e.getVida(), mago.getPuntosMana());
            } else {
                System.out.printf("  %-8s Vida: %3d%n", e.getNombre(), e.getVida());
            }
        }
        System.out.printf("Pociones en inventario: %d%n", inventario.cantidad());
    }
}
