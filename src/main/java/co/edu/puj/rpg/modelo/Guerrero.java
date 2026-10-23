package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enums.EstadoPersonaje;
import co.edu.puj.rpg.modelo.enums.TipoPersonaje;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int fuerzaEscudo) {
        super(nombre, TipoPersonaje.GUERRERO, vida, ataqueBase, posicionX, posicionY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    // Golpe de escudo: tiene un 25% de aturdir al objetivo
    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        int dano = getAtaqueEfectivo() + fuerzaEscudo / 2;
        System.out.printf("%s golpea con su escudo a %s (%d de daño).%n", nombre, objetivo.getNombre(), dano);
        objetivo.defender(dano);
        if (objetivo.estaVivo() && Math.random() < 0.25) {
            objetivo.aplicarEstado(EstadoPersonaje.ATURDIDO, 1);
        }
    }

    @Override
    public void defender(int danoRecibido) {
        int reducido = Math.max(danoRecibido - fuerzaEscudo / 5, 0);
        super.defender(reducido);
    }

    public void atacar(Personaje p) {
        atacar(p, false);
    }

    public void atacar(Personaje p, boolean modoFuria) {
        int dano = modoFuria ? getAtaqueEfectivo() * 2 : getAtaqueEfectivo();
        System.out.printf("%s ataca a %s%s.%n", nombre, p.getNombre(), modoFuria ? " en modo furia" : "");
        p.defender(dano);
    }

    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }
}