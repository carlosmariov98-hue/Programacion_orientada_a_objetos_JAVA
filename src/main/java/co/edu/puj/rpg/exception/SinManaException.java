package co.edu.puj.rpg.exception;

public class SinManaException extends JuegoException {

    private static final long serialVersionUID = 1L;

    private final int manaRequerido;
    private final int manaActual;

    public SinManaException(String personaje, int manaRequerido, int manaActual) {
        super(String.format("%s no tiene maná suficiente: necesita %d y solo tiene %d.",
                personaje, manaRequerido, manaActual));
        this.manaRequerido = manaRequerido;
        this.manaActual = manaActual;
    }

    public SinManaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.manaRequerido = 0;
        this.manaActual = 0;
    }

    public int getManaRequerido() {
        return manaRequerido;
    }

    public int getManaActual() {
        return manaActual;
    }
}