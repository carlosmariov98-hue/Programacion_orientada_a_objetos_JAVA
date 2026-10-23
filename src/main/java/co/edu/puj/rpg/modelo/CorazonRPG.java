package co.edu.puj.rpg.modelo;

/**
 * Núcleo de atributos vitales de un Personaje (composición 1..1).
 * Es inmutable: no tiene setters y solo Personaje puede crearlo.
 */
public class CorazonRPG {

    private final int saludBase;
    private final int manaBase;

    public CorazonRPG(int saludBase, int manaBase) {
        this.saludBase = saludBase;
        this.manaBase = manaBase;
    }

    public int getSaludBase() {
        return saludBase;
    }

    public int getManaBase() {
        return manaBase;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Corazón[salud base ").append(saludBase)
                .append(", maná base ").append(manaBase).append("]");
        return sb.toString();
    }
}