package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enemigo.Enemigo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Personaje heroe = new Personaje("Aragorn", 100, 15, 50.0);
        Enemigo enemigo = new Enemigo("Orco", 80, 12, 30.0);

        StringBuilder historial = new StringBuilder();
        boolean huir = false;
        int ronda = 0;

        System.out.printf("Un %s salvaje aparece frente a %s.%n", enemigo.getNombre(), heroe.getNombre());

        do {
            ronda++;
            boolean turnoGastado = false;

            System.out.printf("%n===== Ronda %d =====%n", ronda);
            System.out.println("1. Atacar");
            System.out.println("2. Usar poción");
            System.out.println("3. Ver estado");
            System.out.println("4. Huir");
            System.out.print("Elige una opción: ");

            int opcion = -1;
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
            } else {
                sc.next();
            }

            if (opcion >= 1 && opcion <= 4) {
                switch (opcion) {
                    case 1: {
                        int vidaAntes = enemigo.getPuntosVida();
                        heroe.atacar(enemigo);
                        int damage = vidaAntes - enemigo.getPuntosVida();
                        historial.append(String.format("Ronda %d: %s ataca a %s y le causa %d de daño.%n",
                                ronda, heroe.getNombre(), enemigo.getNombre(), damage));
                        if (!enemigo.isEstaVivo()) {
                            historial.append(String.format("Ronda %d: %s ha sido derrotado.%n",
                                    ronda, enemigo.getNombre()));
                        }
                        turnoGastado = true;
                        break;
                    }
                    case 2: {
                        int vidaAntes = heroe.getPuntosVida();
                        heroe.usarPocion(30);
                        int curado = heroe.getPuntosVida() - vidaAntes;
                        historial.append(String.format("Ronda %d: %s usa una poción y recupera %d de vida.%n",
                                ronda, heroe.getNombre(), curado));
                        turnoGastado = true;
                        break;
                    }
                    case 3: {
                        mostrarEstado(heroe, enemigo);
                        break;
                    }
                    case 4: {
                        huir = true;
                        historial.append(String.format("Ronda %d: %s huyó del combate.%n",
                                ronda, heroe.getNombre()));
                        break;
                    }
                }
            } else {
                System.out.println("Opción inválida, escribe un número del 1 al 4.");
            }

            // Si el héroe hizo algo y el enemigo sigue vivo, contraataca
            if (turnoGastado && enemigo.isEstaVivo()) {
                int vidaAntes = heroe.getPuntosVida();
                enemigo.atacar(heroe);
                int dano = vidaAntes - heroe.getPuntosVida();
                historial.append(String.format("Ronda %d: %s ataca a %s y le causa %d de daño.%n",
                        ronda, enemigo.getNombre(), heroe.getNombre(), dano));
                if (!heroe.isEstaVivo()) {
                    historial.append(String.format("Ronda %d: %s ha caído en combate.%n",
                            ronda, heroe.getNombre()));
                }
            }

            System.out.println("\n--- Historial de batalla ---");
            System.out.print(historial);

        } while (heroe.isEstaVivo() && enemigo.isEstaVivo() && !huir);

        System.out.println();
        if (huir) {
            System.out.println("Escapaste de la arena.");
        } else if (!heroe.isEstaVivo()) {
            System.out.println("Fuiste derrotado. Fin del juego.");
        } else {
            System.out.printf("¡Victoria! %s derrotó al %s.%n", heroe.getNombre(), enemigo.getNombre());
        }

        sc.close();
    }

    private static void mostrarEstado(Personaje heroe, Enemigo enemigo) {
        System.out.println("\n--- Estado actual ---");
        System.out.printf("Héroe: %s | Nivel: %d | Vida: %d/%d | Ataque: %d | Oro: %.2f%n",
                heroe.getNombre(), heroe.getNivel(), heroe.getPuntosVida(),
                heroe.getVidaMaxima(), heroe.getPuntosAtaque(), heroe.getOro());
        System.out.printf("Enemigo: %s | Nivel: %d | Vida: %d/%d | Ataque: %d%n",
                enemigo.getNombre(), enemigo.getNivel(), enemigo.getPuntosVida(),
                enemigo.getVidaMaxima(), enemigo.getPuntosAtaque());
    }
}