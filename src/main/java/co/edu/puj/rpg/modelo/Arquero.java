package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enums.EstadoPersonaje;
import co.edu.puj.rpg.modelo.enums.TipoPersonaje;

public class Arquero extends Personaje {

    private static final int COSTO_VIDA_DISPARO = 5;

    public Arquero(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        super(nombre, TipoPersonaje.ARQUERO, vida, ataqueBase, posicionX, posicionY, 30);
    }

    // Flecha envenenada: paga vida, pega más fuerte y envenena al objetivo.
    // Si no le alcanza la vida, consumirVida() lanza VidaInsuficienteException
    // (no verificada) y la captura quien llame a este método.
    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        consumirVida(COSTO_VIDA_DISPARO);
        int dano = getAtaqueEfectivo() + 10;
        System.out.printf("%s dispara una flecha envenenada a %s (%d de daño).%n",
                nombre, objetivo.getNombre(), dano);
        objetivo.defender(dano);
        if (objetivo.estaVivo()) {
            objetivo.aplicarEstado(EstadoPersonaje.ENVENENADO, 3);
        }
    }

    public void atacar(Personaje p) {
        atacar(p, false);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        int dano = modoFuria ? getAtaqueEfectivo() * 2 : getAtaqueEfectivo();
        System.out.printf("%s dispara a %s%s.%n", nombre, p.getNombre(), modoFuria ? " con furia" : "");
        p.defender(dano);
    }
}