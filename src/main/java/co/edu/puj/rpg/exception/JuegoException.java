package co.edu.puj.rpg.exception;

/**
 * Base de las excepciones verificadas del dominio del juego.
 */
public abstract class JuegoException extends Exception {

    private static final long serialVersionUID = 1L;

    protected JuegoException(String mensaje) {
        super(mensaje);
    }

    // Para reenvolver otra excepción sin perder la causa original
    protected JuegoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}