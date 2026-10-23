package co.edu.puj.rpg.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Cuenta cuántas veces se ha derrotado a cada tipo de enemigo.
 */
public class Bestiario {

    private final Map<String, Integer> conteo = new HashMap<>();

    // Devuelve true si es la primera victoria contra ese enemigo
    public boolean registrarVictoria(String enemigo) {
        if (enemigo == null || enemigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del enemigo no puede estar vacío.");
        }
        String clave = enemigo.trim();
        boolean primeraVez = !conteo.containsKey(clave);
        // getOrDefault evita el NullPointerException con enemigos nuevos
        conteo.put(clave, conteo.getOrDefault(clave, 0) + 1);
        return primeraVez;
    }

    public boolean fueDerrotado(String enemigo) {
        return conteo.containsKey(enemigo);
    }

    public int getVictorias(String enemigo) {
        return conteo.getOrDefault(enemigo, 0);
    }

    public int totalVictorias() {
        int total = 0;
        for (int cantidad : conteo.values()) {
            total += cantidad;
        }
        return total;
    }

    public boolean estaVacio() {
        return conteo.isEmpty();
    }

    // Vista de solo lectura para que nadie de afuera modifique los contadores
    public Map<String, Integer> obtenerRegistro() {
        return Collections.unmodifiableMap(conteo);
    }

    public void mostrarReporte() {
        if (conteo.isEmpty()) {
            System.out.println("(aún no hay victorias registradas)");
            return;
        }
        System.out.printf("%-20s %s%n", "Enemigo", "Victorias");
        for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
            System.out.printf("%-20s %5d%n", entry.getKey(), entry.getValue());
        }
        System.out.printf("%-20s %5d%n", "TOTAL", totalVictorias());
    }
}