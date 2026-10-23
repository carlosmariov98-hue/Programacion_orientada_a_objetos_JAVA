package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enums.RaridadItem;

public class Pocion {

    private String nombre;
    private int curaBase;
    private RaridadItem rareza;

    public Pocion(String nombre, int curaBase) {
        this(nombre, curaBase, RaridadItem.COMUN);
    }

    public Pocion(String nombre, int curaBase, RaridadItem rareza) {
        this.nombre = nombre;
        this.curaBase = Math.max(curaBase, 0);
        this.rareza = (rareza != null) ? rareza : RaridadItem.COMUN;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadCura() {
        return (int) Math.round(curaBase * rareza.getMultiplicadorStat());
    }

    public RaridadItem getRareza() {
        return rareza;
    }

    @Override
    public String toString() {
        return nombre + " [" + rareza + ", +" + getCantidadCura() + " vida]";
    }
}