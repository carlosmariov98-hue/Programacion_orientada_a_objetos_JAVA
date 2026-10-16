package com.taller.poo.seccion1;

public class Vehiculo {

    private String marca;
    private String modelo;


    private Double kilometrajeActual;
    private Integer velocidadMaxima;

    // Seleccionamos un Wrapper para el estado del motor (permite representar un estado nulo o desconocido antes de encender por primera vez)
    private Boolean motorEncendido;

    public Vehiculo() {
    }

    public Vehiculo(String marca, String modelo, Double kilometrajeActual, Integer velocidadMaxima, Boolean motorEncendido) {
        this.marca = marca;
        this.modelo = modelo;
        this.kilometrajeActual = kilometrajeActual;
        this.velocidadMaxima = velocidadMaxima;
        this.motorEncendido = motorEncendido;
    }


    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getKilometrajeActual() {
        return kilometrajeActual;
    }

    public void setKilometrajeActual(Double kilometrajeActual) {
        this.kilometrajeActual = kilometrajeActual;
    }

    public Integer getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public void setVelocidadMaxima(Integer velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public Boolean getMotorEncendido() {
        return motorEncendido;
    }

    public void setMotorEncendido(Boolean motorEncendido) {
        this.motorEncendido = motorEncendido;
    }
}
}
