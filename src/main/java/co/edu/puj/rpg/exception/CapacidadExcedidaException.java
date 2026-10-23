package co.edu.puj.rpg.exception;

public class CapacidadExcedidaException extends JuegoException {

    private static final long serialVersionUID = 1L;

    private final int capacidadMaxima;

    public CapacidadExcedidaException(String contenedor, int capacidadMaxima) {
        super(String.format("El %s está lleno (capacidad máxima: %d).", contenedor, capacidadMaxima));
        this.capacidadMaxima = capacidadMaxima;
    }

    public CapacidadExcedidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.capacidadMaxima = 0;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}