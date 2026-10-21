package co.edu.puj.rpg.modelo;

public class Pocion {

    private String nombre;
    private int cantidadCura;

    public Pocion(String nombre, int cantidadCura) {
        this.nombre = nombre;
        this.cantidadCura = cantidadCura;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidadCura() {
        return cantidadCura;
    }

    @Override
    public String toString() {
        return nombre + " (+" + cantidadCura + " vida)";
    }
}
