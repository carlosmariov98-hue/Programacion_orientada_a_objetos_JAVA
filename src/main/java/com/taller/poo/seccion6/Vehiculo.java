package com.taller.poo.seccion6;

public class Vehiculo {
    private int velocidadMaxima;
    private boolean encendido;

    public Vehiculo(int velocidadMaxima, boolean encendido) {
        // ERROR ORIGINAL: "velocidadMaxima = velocidadMaxima;" asignaba el parámetro
        // a sí mismo (sombreado de variables) y el atributo quedaba en 0 / false.
        // CORRECCIÓN: usar this para referirse al atributo.
        this.velocidadMaxima = velocidadMaxima;
        this.encendido = encendido;
    }

    public void imprimirEstado() {
        System.out.printf("Velocidad Máxima: %d | Encendido: %b%n", velocidadMaxima, encendido);
    }
}