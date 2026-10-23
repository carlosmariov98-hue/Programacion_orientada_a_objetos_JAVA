package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.modelo.enums.TipoPersonaje;

public class Mago extends Personaje {

    private static final int COSTO_HECHIZO = 20;

    public Mago(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int puntosMana) {
        super(nombre, TipoPersonaje.MAGO, vida, ataqueBase, posicionX, posicionY, puntosMana);
    }

    // Bola de fuego: gasta maná y hace el doble del ataque efectivo
    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        try {
            lanzarHechizo(COSTO_HECHIZO);
        } catch (SinManaException e) {
            System.out.println(e.getMessage());
            return;
        }
        int dano = getAtaqueEfectivo() * 2;
        System.out.printf("%s lanza una bola de fuego a %s (%d de daño).%n", nombre, objetivo.getNombre(), dano);
        objetivo.defender(dano);
    }

    public void atacar(Personaje p) {
        atacar(p, false);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        int dano = modoFuria ? getAtaqueEfectivo() : getAtaqueEfectivo() / 2;
        System.out.printf("%s golpea a %s con su bastón.%n", nombre, p.getNombre());
        p.defender(dano);
    }

    public boolean puedeLanzarHechizo() {
        return getManaActual() >= COSTO_HECHIZO;
    }

    public int getPuntosMana() {
        return getManaActual();
    }
}