package co.edu.puj.rpg.principal;

import co.edu.puj.rpg.engine.Bestiario;
import co.edu.puj.rpg.engine.Estadisticas;
import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.modelo.Arquero;
import co.edu.puj.rpg.modelo.Guerrero;
import co.edu.puj.rpg.modelo.Mago;
import co.edu.puj.rpg.modelo.PerfilJugador;
import co.edu.puj.rpg.modelo.Personaje;
import co.edu.puj.rpg.modelo.enums.TipoPersonaje;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Personaje> personajes = new ArrayList<>();
        Bestiario bestiario = new Bestiario();
        Estadisticas estadisticas = new Estadisticas();
        PerfilJugador perfil = new PerfilJugador("Jugador 1");
        int opcion;

        do {
            System.out.println("\n=== ARENA DE HÉROES RPG ===");
            System.out.println("1. Crear Personaje");
            System.out.println("2. Asignar Habilidad Única");
            System.out.println("3. Registrar Victoria en Bestiario");
            System.out.println("4. Ejecutar Ataque Especial");
            System.out.println("5. Consultar Bestiario y Logros");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    crearPersonaje(personajes);
                    break;
                case 2:
                    asignarHabilidad(personajes);
                    break;
                case 3:
                    registrarVictoria(bestiario, perfil);
                    break;
                case 4:
                    ejecutarAtaqueEspecial(personajes, estadisticas);
                    break;
                case 5:
                    consultar(personajes, bestiario, perfil, estadisticas);
                    break;
                case 0:
                    System.out.println("Cerrando la Arena de Héroes...");
                    break;
                default:
                    System.err.println("Opción inválida, elige un número del 0 al 5.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    // ---------- Opción 1 ----------

    private static void crearPersonaje(List<Personaje> personajes) {
        String nombre = leerTexto("Nombre del personaje: ");
        if (nombre.isEmpty()) {
            System.err.println("El nombre no puede estar vacío.");
            return;
        }

        TipoPersonaje[] tipos = TipoPersonaje.values();
        System.out.println("Tipo de personaje:");
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (ataque +%.1f, defensa +%.1f)%n", i + 1, tipos[i],
                    tipos[i].getBonificadorAtaqueBase(), tipos[i].getBonificadorDefensaBase());
        }
        int eleccion = leerEntero("Elige el tipo: ");
        if (eleccion < 1 || eleccion > tipos.length) {
            System.err.println("Tipo inválido, no se creó el personaje.");
            return;
        }

        TipoPersonaje tipo = tipos[eleccion - 1];
        Personaje nuevo = switch (tipo) {
            case GUERRERO -> new Guerrero(nombre, 100, 15, 0, 0, 10);
            case MAGO -> new Mago(nombre, 80, 20, 0, 0, 50);
            case ARQUERO -> new Arquero(nombre, 90, 17, 0, 0);
        };
        personajes.add(nuevo);
        System.out.printf("Personaje creado: %s (%s) | estado: %s | vida: %d | maná: %d%n",
                nuevo.getNombre(), nuevo.getTipoPersonaje(), nuevo.getEstado(),
                nuevo.getVida(), nuevo.getManaActual());
    }

    // ---------- Opción 2 ----------

    private static void asignarHabilidad(List<Personaje> personajes) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        String habilidad = leerTexto("Nombre de la habilidad: ");
        if (habilidad.isEmpty()) {
            System.err.println("La habilidad no puede estar vacía.");
            return;
        }
        try {
            if (p.aprenderHabilidad(habilidad)) {
                System.out.printf("%s aprendió '%s' (%d/%d habilidades).%n", p.getNombre(), habilidad,
                        p.obtenerHabilidades().size(), Personaje.MAX_HABILIDADES);
            } else {
                System.out.printf("%s ya conoce la habilidad '%s'. No se agregó de nuevo.%n",
                        p.getNombre(), habilidad);
            }
        } catch (CapacidadExcedidaException e) {
            System.err.println(e.getMessage());
        }
    }

    // ---------- Opción 3 ----------

    private static void registrarVictoria(Bestiario bestiario, PerfilJugador perfil) {
        String enemigo = leerTexto("Nombre del enemigo derrotado: ");
        if (enemigo.isEmpty()) {
            System.err.println("El nombre del enemigo no puede estar vacío.");
            return;
        }
        boolean primeraVez = bestiario.registrarVictoria(enemigo);
        System.out.printf("Victoria registrada. '%s' ha sido derrotado %d vez/veces.%n",
                enemigo, bestiario.getVictorias(enemigo));

        if (primeraVez) {
            String logro = "Primera victoria contra " + enemigo;
            if (perfil.registrarLogro(logro)) {
                System.out.println("¡Logro desbloqueado: " + logro + "!");
            }
        }
    }

    // ---------- Opción 4 ----------

    private static void ejecutarAtaqueEspecial(List<Personaje> personajes, Estadisticas estadisticas) {
        Personaje p = elegirPersonaje(personajes);
        if (p == null) {
            return;
        }
        System.out.printf("Maná actual de %s: %d%n", p.getNombre(), p.getManaActual());
        int costo = leerEntero("Costo de maná del ataque: ");

        try {
            p.lanzarHechizo(costo);
            System.out.printf("%s ejecuta su ataque especial. Maná restante: %d%n",
                    p.getNombre(), p.getManaActual());
            estadisticas.acumular("Ataques especiales lanzados", 1);
            estadisticas.acumular("Maná gastado", costo);
        } catch (SinManaException e) {
            System.err.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    // ---------- Opción 5 ----------

    private static void consultar(List<Personaje> personajes, Bestiario bestiario,
                                  PerfilJugador perfil, Estadisticas estadisticas) {
        System.out.println("\n--- BESTIARIO ---");
        bestiario.mostrarReporte();

        System.out.println("\n--- LOGROS (orden alfabético) ---");
        if (perfil.cantidadLogros() == 0) {
            System.out.println("(ninguno)");
        }
        for (String logro : perfil.listarLogrosOrdenados()) {
            System.out.println("- " + logro);
        }

        System.out.println("\n--- PERSONAJES ---");
        if (personajes.isEmpty()) {
            System.out.println("(ninguno)");
        }
        for (Personaje p : personajes) {
            System.out.printf("%s [%s | %s] vida: %d | maná: %d | habilidades: %s%n",
                    p.getNombre(), p.getTipoPersonaje(), p.getEstado(), p.getVida(),
                    p.getManaActual(), p.obtenerHabilidades());
        }

        System.out.println("\n--- ESTADÍSTICAS ---");
        estadisticas.mostrarReporte();
    }

    // ---------- Utilidades ----------

    private static Personaje elegirPersonaje(List<Personaje> personajes) {
        if (personajes.isEmpty()) {
            System.err.println("Primero crea un personaje (opción 1).");
            return null;
        }
        if (personajes.size() == 1) {
            System.out.println("Personaje seleccionado: " + personajes.get(0).getNombre());
            return personajes.get(0);
        }
        System.out.println("Personajes disponibles:");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, personajes.get(i).getNombre(),
                    personajes.get(i).getTipoPersonaje());
        }
        int indice = leerEntero("Elige el personaje: ");
        if (indice < 1 || indice > personajes.size()) {
            System.err.println("Selección inválida.");
            return null;
        }
        return personajes.get(indice - 1);
    }

    // Lee un entero y reintenta hasta que el usuario escriba uno válido
    private static int leerEntero(String mensaje) {
        int valor = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = scanner.nextInt();
                valido = true;
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida: debes escribir un número entero.");
            } finally {
                // Limpia el búfer: descarta el salto de línea o el texto que no era número
                scanner.nextLine();
            }
        }
        return valor;
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }
}