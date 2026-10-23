package co.edu.puj.rpg.exception;

/**
 * Excepción no verificada: no obliga a declarar throws ni a usar try-catch.
 */
public class VidaInsuficienteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int costoVida;
    private final int vidaActual;

    public VidaInsuficienteException(String personaje, int costoVida, int vidaActual) {
        super(String.format("%s no puede pagar %d de vida, solo tiene %d.",
                personaje, costoVida, vidaActual));
        this.costoVida = costoVida;
        this.vidaActual = vidaActual;
    }

    public VidaInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.costoVida = 0;
        this.vidaActual = 0;
    }

    public int getCostoVida() {
        return costoVida;
    }

    public int getVidaActual() {
        return vidaActual;
    }
}