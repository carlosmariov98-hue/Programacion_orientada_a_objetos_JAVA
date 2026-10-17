package co.edu.puj.rpg.modelo;

import co.edu.puj.rpg.modelo.enemigo.Enemigo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

    // Constructor completo
    public Personaje(String nombre, int vidaMaxima, int puntosAtaque, double oro) {
        this.nombre = nombre;
        this.vidaMaxima = vidaMaxima;
        this.puntosAtaque = puntosAtaque;
        this.nivel = 1;
        this.estaVivo = true;
        this.setPuntosVida(vidaMaxima);
        this.oro = 0.0;
        this.setOro(oro);
    }

    // Constructor con valores por defecto, solo pide el nombre
    public Personaje(String nombre) {
        this(nombre, 100, 15, 50.0);
    }

    // ---------- Comportamiento ----------

    public void atacar(Enemigo enemigo) {
        if (!this.estaVivo || enemigo == null) {
            return;
        }
        enemigo.recibirDamage(this.puntosAtaque);
    }

    public void usarPocion(int cantidadRestaurada) {
        if (cantidadRestaurada <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida + cantidadRestaurada);
    }

    public void recibirDamage(int cantidadDamage) {
        if (cantidadDamage <= 0) {
            return;
        }
        this.setPuntosVida(this.puntosVida - cantidadDamage);
    }

    // ---------- Setters con validación ----------

    public void setPuntosVida(int vida) {
        if (vida <= 0) {
            this.puntosVida = 0;
            this.estaVivo = false;
        } else if (vida > this.vidaMaxima) {
            this.puntosVida = this.vidaMaxima;
            this.estaVivo = true;
        } else {
            this.puntosVida = vida;
            this.estaVivo = true;
        }
    }

    public void setOro(double oro) {
        if (oro < 0.0) {
            System.out.println("Saldo insuficiente, la operación fue rechazada.");
            return;
        }
        this.oro = oro;
    }

    public void setNivel(int nivel) {
        if (nivel < 1) {
            return;
        }
        this.nivel = nivel;
    }

    // ---------- Getters ----------

    public String getNombre() {
        return nombre;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public int getNivel() {
        return nivel;
    }

    public double getOro() {
        return oro;
    }

    public boolean isEstaVivo() {
        return estaVivo;
    }
}