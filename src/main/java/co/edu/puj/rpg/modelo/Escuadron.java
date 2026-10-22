package co.edu.puj.rpg.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Escuadron {

    private String nombre;
    private List<Personaje> miembros; // Agregación (0..*)

    public Escuadron(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>(); // Inicialización de la colección
    }

    // El Personaje se crea afuera y llega como parámetro (agregación)
    public void reclutarPersonaje(Personaje p) {
        if (p == null) {
            System.out.println("No se puede reclutar un personaje nulo.");
            return;
        }
        if (miembros.contains(p)) {
            System.out.printf("%s ya pertenece al escuadrón %s.%n", p.getNombre(), nombre);
            return;
        }
        miembros.add(p);
        System.out.printf("%s fue reclutado en el escuadrón %s.%n", p.getNombre(), nombre);
    }

    // El personaje sale de la lista pero sigue existiendo con todo su estado
    public void expulsarPersonaje(Personaje p) {
        if (p != null && miembros.remove(p)) {
            System.out.printf("%s fue expulsado del escuadrón %s.%n", p.getNombre(), nombre);
        } else {
            System.out.println("Ese personaje no pertenece al escuadrón " + nombre + ".");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getMiembros() {
        return Collections.unmodifiableList(miembros);
    }

    public int cantidadMiembros() {
        return miembros.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Escuadrón ").append(nombre).append(" (")
                .append(miembros.size()).append(" integrantes)");
        if (!miembros.isEmpty()) {
            sb.append(": ");
            for (int i = 0; i < miembros.size(); i++) {
                Personaje p = miembros.get(i);
                sb.append(p.getNombre()).append(" [").append(p.getClass().getSimpleName()).append("]");
                if (i < miembros.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
