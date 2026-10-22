package co.edu.puj.rpg.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gremio {

    private String nombre;
    private List<Personaje> miembros;

    public Gremio(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
    }

    // Sincronización cruzada con Personaje.unirseAGremio()
    public void agregarMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        if (!miembros.contains(p)) {
            miembros.add(p);
        }
        // Si el personaje todavía no apunta a este gremio, se lo avisamos.
        // Cuando ya apunta a este gremio la cadena se corta (sin recursión infinita).
        if (p.getGremio() != this) {
            p.unirseAGremio(this);
        }
    }

    // Sincronización cruzada con Personaje.abandonarGremio()
    public void removerMiembro(Personaje p) {
        if (p == null) {
            return;
        }
        boolean estaba = miembros.remove(p);
        if (estaba && p.getGremio() == this) {
            p.abandonarGremio();
        }
    }

    public String getNombre() {
        return nombre;
    }

    // Copia de solo lectura: nadie de afuera puede romper la sincronización
    public List<Personaje> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    public int cantidadMiembros() {
        return miembros.size();
    }

    // Solo nombres de los miembros: llamar a toString() de cada Personaje
    // provocaría StackOverflowError (Personaje -> Gremio -> Personaje ...)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gremio ").append(nombre).append(" (")
                .append(miembros.size()).append(" miembros)");
        if (!miembros.isEmpty()) {
            sb.append(": ");
            for (int i = 0; i < miembros.size(); i++) {
                sb.append(miembros.get(i).getNombre());
                if (i < miembros.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}