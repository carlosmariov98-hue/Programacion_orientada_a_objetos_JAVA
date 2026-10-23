package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.exception.CapacidadExcedidaException;
import co.edu.puj.rpg.exception.SinManaException;
import co.edu.puj.rpg.exception.VidaInsuficienteException;
import co.edu.puj.rpg.modelo.enums.EstadoPersonaje;
import co.edu.puj.rpg.modelo.enums.TipoPersonaje;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Personaje implements ICombatiente {

    public static final int MAX_HABILIDADES = 5;
    private static final int MITIGACION_BASE = 2;
    private static final int DANO_VENENO = 3;

    protected String nombre;
    protected int vida;
    protected int ataqueBase;
    protected int posicionX;
    protected int posicionY;

    private int nivel;
    private Arma armaEquipada;              // Asociación unidireccional (0..1)
    private Gremio gremio;                  // Asociación bidireccional (0..* a 1)
    private final CorazonRPG corazon;       // Composición (1..1)

    private TipoPersonaje tipoPersonaje;
    private EstadoPersonaje estado;
    private int turnosEstado;               // turnos que le quedan al estado alterado
    private int manaActual;
    private final Set<String> habilidades = new HashSet<>();

    public Personaje(String nombre, TipoPersonaje tipoPersonaje, int vida, int ataqueBase,
                     int posicionX, int posicionY) {
        this(nombre, tipoPersonaje, vida, ataqueBase, posicionX, posicionY, 0);
    }

    public Personaje(String nombre, TipoPersonaje tipoPersonaje, int vida, int ataqueBase,
                     int posicionX, int posicionY, int manaBase) {
        this.nombre = nombre;
        this.tipoPersonaje = Objects.requireNonNull(tipoPersonaje, "El tipo de personaje es obligatorio");
        this.vida = Math.max(vida, 0);
        this.ataqueBase = ataqueBase;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.nivel = 1;
        this.armaEquipada = null;
        this.gremio = null;
        this.manaActual = Math.max(manaBase, 0);
        // Composición: el corazón se crea aquí adentro, nunca llega desde afuera
        this.corazon = new CorazonRPG(this.vida, this.manaActual);
        this.estado = (this.vida > 0) ? EstadoPersonaje.VIVO : EstadoPersonaje.DERROTADO;
        this.turnosEstado = 0;
    }

    // Cada subclase define su propio ataque especial
    public abstract void realizarAtaqueEspecial(Personaje objetivo);

    // ---------- Combate ----------

    // Mitigación base más el bonificador de defensa del tipo de personaje
    @Override
    public void defender(int danoRecibido) {
        int mitigacion = MITIGACION_BASE + (int) tipoPersonaje.getBonificadorDefensaBase();
        int danoReal = Math.max(danoRecibido - mitigacion, 0);
        System.out.printf("%s se defiende y recibe %d de daño.%n", nombre, danoReal);
        recibirDano(danoReal);
    }

    public void recibirDano(int cantidad) {
        if (cantidad <= 0) {
            return;
        }
        this.vida -= cantidad;
        if (this.vida <= 0) {
            this.vida = 0;
            this.estado = EstadoPersonaje.DERROTADO;
            this.turnosEstado = 0;
        }
    }

    public void curar(int cantidad) {
        if (cantidad <= 0 || !estaVivo()) {
            return;
        }
        this.vida = Math.min(this.vida + cantidad, corazon.getSaludBase());
    }

    public boolean estaVivo() {
        return estado != EstadoPersonaje.DERROTADO;
    }

    // ---------- Hechizos y costos (excepciones de dominio) ----------

    // Gasta maná. Lanza SinManaException (verificada) si no alcanza.
    public void lanzarHechizo(int costoMana) throws SinManaException {
        if (costoMana <= 0) {
            throw new IllegalArgumentException("El costo de maná debe ser mayor que 0.");
        }
        if (costoMana > manaActual) {
            throw new SinManaException(nombre, costoMana, manaActual);
        }
        manaActual -= costoMana;
    }

    // Paga con vida. Lanza VidaInsuficienteException (no verificada) si no puede.
    public void consumirVida(int costoVida) {
        if (costoVida <= 0) {
            throw new IllegalArgumentException("El costo de vida debe ser mayor que 0.");
        }
        if (costoVida >= vida) {
            throw new VidaInsuficienteException(nombre, costoVida, vida);
        }
        vida -= costoVida;
    }

    // ---------- Habilidades (HashSet) ----------

    // true si se aprendió, false si ya la conocía. Si el libro está lleno lanza la excepción.
    public boolean aprenderHabilidad(String habilidad) throws CapacidadExcedidaException {
        if (habilidad == null || habilidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La habilidad no puede estar vacía.");
        }
        String nombreHabilidad = habilidad.trim();
        if (habilidades.contains(nombreHabilidad)) {
            return false; // duplicado: no se toca la colección
        }
        if (habilidades.size() >= MAX_HABILIDADES) {
            throw new CapacidadExcedidaException("libro de habilidades de " + nombre, MAX_HABILIDADES);
        }
        return habilidades.add(nombreHabilidad);
    }

    public Set<String> obtenerHabilidades() {
        return Collections.unmodifiableSet(habilidades);
    }

    // ---------- Estados alterados ----------

    public void aplicarEstado(EstadoPersonaje nuevoEstado, int turnos) {
        if (nuevoEstado == null || this.estado == EstadoPersonaje.DERROTADO) {
            return;
        }
        if (nuevoEstado == EstadoPersonaje.DERROTADO) {
            this.vida = 0;
            this.estado = EstadoPersonaje.DERROTADO;
            this.turnosEstado = 0;
            return;
        }
        this.estado = nuevoEstado;
        this.turnosEstado = (nuevoEstado == EstadoPersonaje.VIVO) ? 0 : Math.max(turnos, 1);
        if (nuevoEstado != EstadoPersonaje.VIVO) {
            System.out.printf("%s queda %s por %d turno(s).%n", nombre, nuevoEstado, this.turnosEstado);
        }
    }

    // Se llama al empezar el turno. Devuelve false si el personaje no puede actuar.
    public boolean iniciarTurno() {
        if (estado == EstadoPersonaje.DERROTADO) {
            return false;
        }
        boolean puedeActuar = true;

        if (estado == EstadoPersonaje.ENVENENADO) {
            System.out.printf("%s sufre %d de daño por veneno.%n", nombre, DANO_VENENO);
            recibirDano(DANO_VENENO);
            if (estado == EstadoPersonaje.DERROTADO) {
                System.out.printf("%s cae por el veneno.%n", nombre);
                return false;
            }
        } else if (estado == EstadoPersonaje.ATURDIDO) {
            System.out.printf("%s está aturdido y pierde su turno.%n", nombre);
            puedeActuar = false;
        }

        if (estado == EstadoPersonaje.ENVENENADO || estado == EstadoPersonaje.ATURDIDO) {
            turnosEstado--;
            if (turnosEstado <= 0) {
                estado = EstadoPersonaje.VIVO;
                System.out.printf("%s se recupera del estado alterado.%n", nombre);
            }
        }
        return puedeActuar;
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

    // Daño que se usa en combate: ataque base + bonificador del tipo + daño del arma
    public int getAtaqueEfectivo() {
        int ataque = this.ataqueBase + (int) tipoPersonaje.getBonificadorAtaqueBase();
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
            return;
        }
        this.nivel = nivel;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Gremio getGremio() {
        return gremio;
    }

    public TipoPersonaje getTipoPersonaje() {
        return tipoPersonaje;
    }

    public EstadoPersonaje getEstado() {
        return estado;
    }

    public int getManaActual() {
        return manaActual;
    }

    // No se expone el objeto CorazonRPG, solo sus valores
    public int getSaludBase() {
        return corazon.getSaludBase();
    }

    public int getManaBase() {
        return corazon.getManaBase();
    }

    // Del gremio solo se imprime el nombre para evitar el StackOverflowError
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" ").append(nombre)
                .append(" [").append(tipoPersonaje).append(", nivel ").append(nivel)
                .append(", vida ").append(vida).append(", ").append(estado).append("]")
                .append(" | ").append(corazon)
                .append(" | Arma: ").append(armaEquipada != null ? armaEquipada.toString() : "ninguna")
                .append(" | Gremio: ").append(gremio != null ? gremio.getNombre() : "ninguno");
        return sb.toString();
    }
}