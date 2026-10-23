package co.edu.puj.rpg.modelo;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class PerfilJugador {

    private String nombre;
    private final Set<String> logros = new TreeSet<>(); // sin repetidos y en orden alfabético

    public PerfilJugador(String nombre) {
        this.nombre = nombre;
    }

    // true si el logro es nuevo, false si ya estaba desbloqueado
    public boolean registrarLogro(String logro) {
        if (logro == null || logro.trim().isEmpty()) {
            throw new IllegalArgumentException("El logro no puede estar vacío.");
        }
        return logros.add(logro.trim());
    }

    // Solo lectura, y al iterar sale en orden alfabético natural
    public Set<String> listarLogrosOrdenados() {
        return Collections.unmodifiableSet(logros);
    }

    public int cantidadLogros() {
        return logros.size();
    }

    public String getNombre() {
        return nombre;
    }
}