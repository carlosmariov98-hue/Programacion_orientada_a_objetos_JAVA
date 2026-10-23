package co.edu.puj.rpg.modelo.enums;

public enum TipoPersonaje {

    GUERRERO(2.0, 2.0),
    MAGO(3.0, 0.0),
    ARQUERO(3.0, 1.0);

    private final double bonificadorAtaqueBase;
    private final double bonificadorDefensaBase;

    private TipoPersonaje(double bonificadorAtaqueBase, double bonificadorDefensaBase) {
        this.bonificadorAtaqueBase = bonificadorAtaqueBase;
        this.bonificadorDefensaBase = bonificadorDefensaBase;
    }

    public double getBonificadorAtaqueBase() {
        return bonificadorAtaqueBase;
    }

    public double getBonificadorDefensaBase() {
        return bonificadorDefensaBase;
    }
}