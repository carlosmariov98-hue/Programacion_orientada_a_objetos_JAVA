package co.edu.puj.rpg.engine;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Estadísticas globales del juego. Al ser un TreeMap, las claves
 * siempre se recorren en orden alfabético.
 */
public class Estadisticas {

    private final Map<String, Double> valores = new TreeMap<>();

    public void registrar(String clave, double valor) {
        validarClave(clave);
        valores.put(clave, valor);
    }

    // Suma el valor a lo que ya hubiera (o a 0 si la clave es nueva)
    public void acumular(String clave, double valor) {
        validarClave(clave);
        valores.put(clave, valores.getOrDefault(clave, 0.0) + valor);
    }

    public boolean existe(String clave) {
        return valores.containsKey(clave);
    }

    public double obtener(String clave) {
        if (valores.containsKey(clave)) {
            return valores.get(clave);
        }
        return 0.0;
    }

    public Map<String, Double> getValores() {
        return Collections.unmodifiableMap(valores);
    }

    public void mostrarReporte() {
        if (valores.isEmpty()) {
            System.out.println("(sin estadísticas todavía)");
            return;
        }
        for (Map.Entry<String, Double> entry : valores.entrySet()) {
            System.out.printf("%-30s %8.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private void validarClave(String clave) {
        if (clave == null || clave.trim().isEmpty()) {
            throw new IllegalArgumentException("La clave no puede estar vacía.");
        }
    }
}