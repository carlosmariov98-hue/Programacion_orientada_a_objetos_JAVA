package co.edu.puj.rpg.modelo;

public class Arma {

    private String nombre;
    private int danioBase;

    public Arma(String nombre, int danioBase) {
        this.nombre = nombre;
        this.danioBase = Math.max(danioBase, 0);
    }

    public String getNombre() {
        return nombre;
    }

    public int getDanioBase() {
        return danioBase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append(" (daño ").append(danioBase).append(")");
        return sb.toString();
    }
}