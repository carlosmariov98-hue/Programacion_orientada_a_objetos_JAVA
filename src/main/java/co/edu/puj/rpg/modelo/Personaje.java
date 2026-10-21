package co.edu.puj.rpg.modelo;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    // Tope de vida para que las pociones no curen de más
    private final int vidaMaxima;

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this.nombre = nombre;
        this.vida = Math.max(vida, 0);
        this.vidaMaxima = this.vida;
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    // Cada subclase define su propio ataque especial
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    // Mitigación base: cualquier personaje absorbe 2 puntos de daño
    @Override
    public void defender(int danoRecibido) {
        int danoReal = Math.max(danoRecibido - 2, 0);
        System.out.printf("%s se defiende y recibe %d de daño.%n", nombre, danoReal);
        recibirDano(danoReal);
    }

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        this.vida -= cantidad;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public void curar(int cantidad) {
        if (cantidad <= 0 || !estaVivo()) {
            return;
        }
        this.vida = Math.min(this.vida + cantidad, vidaMaxima);
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }
}