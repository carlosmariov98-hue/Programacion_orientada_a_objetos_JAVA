package co.edu.puj.rpg.modelo;

public class Guerrero extends Personaje {

    protected int fuerzaEscudo;

    public Guerrero(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int fuerzaEscudo) {
        super(nombre, vida, ataqueBase, posicionX, posicionY);
        this.fuerzaEscudo = fuerzaEscudo;
    }

    // Golpe de escudo: el ataque base más la mitad de la fuerza del escudo
    @Override
    public void realizarAtaqueEspecial(Personaje objetivo) {
        int dano = getAtaqueEfectivo() + fuerzaEscudo / 2;
        System.out.printf("%s golpea con su escudo a %s (%d de daño).%n", nombre, objetivo.getNombre(), dano);
        objetivo.defender(dano);
    }

    // El escudo reduce un poco más el daño antes de la mitigación base
    @Override
    public void defender(int danoRecibido) {
        int reducido = Math.max(danoRecibido - fuerzaEscudo / 5, 0);
        super.defender(reducido);
    }

    // Sobrecarga: ataque normal
    public void atacar(Personaje p) {
        atacar(p, false);
    }

    // Sobrecarga: con modo furia el daño se duplica
    public void atacar(Personaje p, boolean modoFuria) {
        int dano = modoFuria ? getAtaqueEfectivo() * 2 : getAtaqueEfectivo();
        System.out.printf("%s ataca a %s%s.%n", nombre, p.getNombre(), modoFuria ? " en modo furia" : "");
        p.defender(dano);
    }

    public int getFuerzaEscudo() {
        return fuerzaEscudo;
    }
}