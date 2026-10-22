package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.modelo.Arma;
import co.edu.puj.rpg.modelo.Escuadron;
import co.edu.puj.rpg.modelo.Gremio;
import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.Personaje;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AplicacionArenaRPG {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        // Colecciones globales (los personajes existen aunque no estén en ningún escuadrón)
        List<Personaje> personajes = new ArrayList<>();
        List<Gremio> gremios = new ArrayList<>();
        List<Escuadron> escuadrones = new ArrayList<>();

        do {
            System.out.println("\n=== MENÚ ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Crear Gremio");
            System.out.println("3. Reclutar Personaje a Escuadrón");
            System.out.println("4. Equipar Arma a Personaje");
            System.out.println("5. Mostrar Estado Detallado (Personaje y Corazón RPG)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    crearPersonaje(personajes);
                    break;
                case 2:
                    crearGremio(gremios, personajes);
                    break;
                case 3:
                    gestionarEscuadron(personajes, escuadrones);
                    break;
                case 4:
                    gestionarArma(personajes);
                    break;
                case 5:
                    mostrarEstado(personajes, gremios, escuadrones);
                    break;
                case 6:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);

        scanner.close();
    }

    // ---------- Opción 1 ----------

    private static void crearPersonaje(List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del personaje: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        System.out.println("Clase: 1. Guerrero  2. Mago");
        int clase = leerEntero();
        System.out.print("Nivel inicial: ");
        int nivel = leerEntero();

        Personaje nuevo;
        if (clase == 1) {
            nuevo = new Guerrero(nombre, 100, 15, 0, 0, 10);
        } else if (clase == 2) {
            nuevo = new Mago(nombre, 80, 20, 0, 0, 50);
        } else {
            System.out.println("Clase inválida, no se creó el personaje.");
            return;
        }
        nuevo.setNivel(nivel); // si el nivel es menor que 1 se queda en 1
        personajes.add(nuevo);
        System.out.printf("Personaje creado: %s (%s, nivel %d).%n",
                nuevo.getNombre(), nuevo.getClass().getSimpleName(), nuevo.getNivel());
    }

    // ---------- Opción 2 ----------

    private static void crearGremio(List<Gremio> gremios, List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del gremio: ");
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        Gremio gremio = new Gremio(nombre);
        gremios.add(gremio);
        System.out.println("Gremio creado: " + gremio.getNombre());

        // Para poder probar la asociación bidireccional
        String respuesta = leerTexto("¿Inscribir un personaje en este gremio? (s/n): ");
        while (respuesta.equalsIgnoreCase("s") && !personajes.isEmpty()) {
            Personaje p = seleccionarPersonaje(personajes);
            if (p != null) {
                Gremio anterior = p.getGremio();
                p.unirseAGremio(gremio);
                if (anterior != null) {
                    System.out.printf("%s dejó el gremio %s.%n", p.getNombre(), anterior.getNombre());
                }
                System.out.printf("%s ahora pertenece al gremio %s.%n", p.getNombre(), gremio.getNombre());
            }
            respuesta = leerTexto("¿Inscribir otro personaje? (s/n): ");
        }
        if (respuesta.equalsIgnoreCase("s") && personajes.isEmpty()) {
            System.out.println("Todavía no hay personajes creados.");
        }
    }

    // ---------- Opción 3 ----------

    private static void gestionarEscuadron(List<Personaje> personajes, List<Escuadron> escuadrones) {
        System.out.println("1. Reclutar personaje  2. Expulsar personaje");
        int accion = leerEntero();

        if (accion == 1) {
            if (personajes.isEmpty()) {
                System.out.println("Primero crea al menos un personaje.");
                return;
            }
            Personaje p = seleccionarPersonaje(personajes);
            if (p == null) {
                return;
            }
            String nombreEscuadron = leerTexto("Nombre del escuadrón (se crea si no existe): ");
            if (nombreEscuadron.isEmpty()) {
                System.out.println("El nombre no puede estar vacío.");
                return;
            }
            Escuadron escuadron = buscarEscuadron(escuadrones, nombreEscuadron);
            if (escuadron == null) {
                escuadron = new Escuadron(nombreEscuadron);
                escuadrones.add(escuadron);
                System.out.println("Escuadrón creado: " + escuadron.getNombre());
            }
            escuadron.reclutarPersonaje(p);

        } else if (accion == 2) {
            if (escuadrones.isEmpty()) {
                System.out.println("No hay escuadrones creados.");
                return;
            }
            System.out.println("Escuadrones:");
            for (int i = 0; i < escuadrones.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, escuadrones.get(i).getNombre());
            }
            System.out.print("Elige el escuadrón: ");
            int indice = leerEntero();
            if (indice < 1 || indice > escuadrones.size()) {
                System.out.println("Escuadrón inválido.");
                return;
            }
            Escuadron escuadron = escuadrones.get(indice - 1);
            if (escuadron.cantidadMiembros() == 0) {
                System.out.println("Ese escuadrón no tiene integrantes.");
                return;
            }
            Personaje p = seleccionarPersonaje(escuadron.getMiembros());
            if (p != null) {
                escuadron.expulsarPersonaje(p);
                System.out.printf("%s sigue existiendo en el sistema (está en la lista de personajes: %b).%n",
                        p.getNombre(), personajes.contains(p));
            }
        } else {
            System.out.println("Opción inválida.");
        }
    }

    // ---------- Opción 4 ----------

    private static void gestionarArma(List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.out.println("Primero crea al menos un personaje.");
            return;
        }
        Personaje p = seleccionarPersonaje(personajes);
        if (p == null) {
            return;
        }
        System.out.println("1. Equipar arma nueva  2. Retirar arma");
        int accion = leerEntero();

        if (accion == 1) {
            String nombreArma = leerTexto("Nombre del arma: ");
            System.out.print("Daño base del arma: ");
            int danio = leerEntero();
            if (nombreArma.isEmpty() || danio <= 0) {
                System.out.println("Datos del arma inválidos (nombre vacío o daño menor que 1).");
                return;
            }
            p.equiparArma(new Arma(nombreArma, danio));
            System.out.printf("Ataque total de %s: %d%n", p.getNombre(), p.calcularAtaqueTotal());
        } else if (accion == 2) {
            p.desequiparArma();
            System.out.printf("Ataque total de %s: %d%n", p.getNombre(), p.calcularAtaqueTotal());
        } else {
            System.out.println("Opción inválida.");
        }
    }

    // ---------- Opción 5 ----------

    private static void mostrarEstado(List<Personaje> personajes, List<Gremio> gremios,
                                      List<Escuadron> escuadrones) {
        if (personajes.isEmpty()) {
            System.out.println("No hay personajes registrados todavía.");
            return;
        }

        System.out.println("\n--- PERSONAJES ---");
        System.out.printf("%-10s %-9s %5s %5s %6s %6s %7s  %-18s %-10s%n",
                "Nombre", "Clase", "Nivel", "Vida", "S.Base", "M.Base", "Ataque", "Arma", "Gremio");
        for (Personaje p : personajes) {
            String arma = (p.getArmaEquipada() != null) ? p.getArmaEquipada().getNombre() : "-";
            String gremio = (p.getGremio() != null) ? p.getGremio().getNombre() : "-";
            System.out.printf("%-10s %-9s %5d %5d %6d %6d %7d  %-18s %-10s%n",
                    p.getNombre(), p.getClass().getSimpleName(), p.getNivel(), p.getVida(),
                    p.getSaludBase(), p.getManaBase(), p.calcularAtaqueTotal(), arma, gremio);
        }

        System.out.println("\n--- DETALLE ---");
        for (Personaje p : personajes) {
            System.out.println(p);
        }

        System.out.println("\n--- GREMIOS ---");
        if (gremios.isEmpty()) {
            System.out.println("(ninguno)");
        }
        for (Gremio g : gremios) {
            System.out.println(g);
        }

        System.out.println("\n--- ESCUADRONES ---");
        if (escuadrones.isEmpty()) {
            System.out.println("(ninguno)");
        }
        for (Escuadron e : escuadrones) {
            System.out.println(e);
        }
    }

    // ---------- Utilidades ----------

    private static Personaje seleccionarPersonaje(List<Personaje> lista) {
        System.out.println("Personajes disponibles:");
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, lista.get(i).getNombre(),
                    lista.get(i).getClass().getSimpleName());
        }
        System.out.print("Elige el personaje: ");
        int indice = leerEntero();
        if (indice < 1 || indice > lista.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return lista.get(indice - 1);
    }

    private static Escuadron buscarEscuadron(List<Escuadron> escuadrones, String nombre) {
        for (Escuadron e : escuadrones) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    private static int leerEntero() {
        String linea = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}