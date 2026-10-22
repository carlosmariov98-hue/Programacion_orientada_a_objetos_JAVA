package co.edu.puj.rpg.modelo;

public abstract class Personaje implements ICombatiente {

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    private int nivel;
    private Arma armaEquipada;          // Asociación unidireccional (0..1)
    private Gremio gremio;              // Asociación bidireccional (0..*  a 1)
    private final CorazonRPG corazon;   // Composición (1..1)

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY) {
        this(nombre, vida, ataqueBase, posicionX, posicionY, 0);
    }

    public Personaje(String nombre, int vida, int ataqueBase, int posicionX, int posicionY, int manaBase) {
        this.nombre = nombre;
        this.vida = Math.max(vida, 0);
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.nivel = 1;
        this.armaEquipada = null; // Inicialmente desarmado
        this.gremio = null;
        // Composición: el corazón se crea aquí adentro, nunca llega desde afuera
        this.corazon = new CorazonRPG(this.vida, Math.max(manaBase, 0));
    }

    // Cada subclase define su propio ataque especial
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    // ---------- Combate (guía 2) ----------

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
        this.vida = Math.min(this.vida + cantidad, corazon.getSaludBase());
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // ---------- Asociación unidireccional: Personaje -> Arma ----------

    public void equiparArma(Arma nuevaArma) {
        if (nuevaArma == null) {
            System.out.println("No se puede equipar un arma nula.");
            return;
        }
        this.armaEquipada = nuevaArma;
        System.out.printf("%s ahora lleva equipada: %s.%n", nombre, nuevaArma.getNombre());
    }

    public void desequiparArma() {
        if (this.armaEquipada != null) {
            System.out.printf("%s guarda su arma (%s).%n", nombre, armaEquipada.getNombre());
            this.armaEquipada = null;
        } else {
            System.out.printf("%s no tiene ningún arma para retirar.%n", nombre);
        }
    }

    public int calcularAtaqueTotal() {
        int ataqueTotal = this.nivel * 5;
        if (this.armaEquipada != null) {
            ataqueTotal += this.armaEquipada.getDanioBase();
        }
        return ataqueTotal;
    }

    // Daño que realmente se usa en combate: ataque base + daño del arma (si hay)
    public int getAtaqueEfectivo() {
        int ataque = this.ataqueBase;
        if (this.armaEquipada != null) {
            ataque += this.armaEquipada.getDanioBase();
        }
        return ataque;
    }

    // ---------- Asociación bidireccional: Personaje <-> Gremio ----------

    public void unirseAGremio(Gremio nuevoGremio) {
        if (nuevoGremio == null || nuevoGremio == this.gremio) {
            return;
        }
        // Si ya estaba en otro gremio, primero se retira de ese
        if (this.gremio != null) {
            abandonarGremio();
        }
        this.gremio = nuevoGremio;
        nuevoGremio.agregarMiembro(this);
    }

    public void abandonarGremio() {
        if (this.gremio == null) {
            return;
        }
        Gremio anterior = this.gremio;
        this.gremio = null;
        anterior.removerMiembro(this);
    }

    // ---------- Getters / setters ----------

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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        if (nivel < 1) {
            return; // conserva el nivel actual
        }
        this.nivel = nivel;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
    }

    // No se expone el objeto CorazonRPG, solo sus valores
    public int getSaludBase() {
        return corazon.getSaludBase();
    }

    public int getManaBase() {
        return corazon.getManaBase();
    }

    // Del gremio solo se imprime el nombre: si se llamara a gremio.toString()
    // y este a su vez a toString() de cada miembro, habría StackOverflowError
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" ").append(nombre)
                .append(" [nivel ").append(nivel).append(", vida ").append(vida).append("]")
                .append(" | ").append(corazon)
                .append(" | Arma: ").append(armaEquipada != null ? armaEquipada.toString() : "ninguna")
                .append(" | Gremio: ").append(gremio != null ? gremio.getNombre() : "ninguno");
        return sb.toString();
    }
}