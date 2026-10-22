package co.edu.puj.rpg.modelo;

public class Mago extends Personaje {

    private static final int COSTO_HECHIZO = 20;

    protected int puntosMana;

    public Mago(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int puntosMana) {
        super(nombre, vida, ataqueBase, posicionX, posicionY, puntosMana);
        this.puntosMana = puntosMana;
    }

    // Bola de fuego: gasta maná y hace el doble del ataque base
    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        if (puntosMana < COSTO_HECHIZO) {
            System.out.printf("%s no tiene maná suficiente para lanzar el hechizo.%n", nombre);
            return;
        }
        puntosMana -= COSTO_HECHIZO;
        int dano = getAtaqueEfectivo() * 2;
        System.out.printf("%s lanza una bola de fuego a %s (%d de daño).%n", nombre, objetivo.getNombre(), dano);
        objetivo.defender(dano);
    }

    // Sobrecarga: golpe con el bastón, la mitad del ataque base
    public void atacar(Personaje p) {
        atacar(p, false);
    }

    // Sobrecarga: en modo furia el bastón pega con el ataque base completo
    public void atacar(Personaje p, boolean modoFuria) {
        int dano = modoFuria ? getAtaqueEfectivo() : getAtaqueEfectivo() / 2;
        System.out.printf("%s golpea a %s con su bastón.%n", nombre, p.getNombre());
        p.defender(dano);
    }

    public boolean puedeLanzarHechizo() {
        return puntosMana >= COSTO_HECHIZO;
    }

    public int getPuntosMana() {
        return puntosMana;
    }
}