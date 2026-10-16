package com.taller.poo.seccion6;

public class GestorVehiculos {

    public void encenderMotor() {
        System.out.println("El motor del vehículo ha sido encendido.");
    }

    public static void main(String[] args) {
        // ALTERNATIVA 1 (la usada aquí): crear un objeto e invocar el método sobre él
        GestorVehiculos gestor = new GestorVehiculos();
        gestor.encenderMotor();

        // ALTERNATIVA 2: declarar el método como "public static void encenderMotor()"
        // y llamarlo directamente: encenderMotor();
    }
}