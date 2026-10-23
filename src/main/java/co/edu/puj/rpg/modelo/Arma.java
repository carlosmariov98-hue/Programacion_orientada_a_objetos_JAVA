package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enums.RaridadItem;

public class Arma {

    private String nombre;
    private int danioBase;
    private RaridadItem rareza;

    public Arma(String nombre, int danioBase) {
        this(nombre, danioBase, RaridadItem.COMUN);
    }

    public Arma(String nombre, int danioBase, RaridadItem rareza) {
        this.nombre = nombre;
        this.danioBase = Math.max(danioBase, 0);
        this.rareza = (rareza != null) ? rareza : RaridadItem.COMUN;
    }

    public String getNombre() {
        return nombre;
    }

    // El daño real se escala con el multiplicador de la rareza
    public int getDanioBase() {
        return (int) Math.round(danioBase * rareza.getMultiplicadorStat());
    }

    public RaridadItem getRareza() {
        return rareza;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" [").append(rareza).append(", daño ").append(getDanioBase()).append("]");
        return sb.toString();
    }
}