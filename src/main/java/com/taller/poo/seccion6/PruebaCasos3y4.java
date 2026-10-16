package com.taller.poo.seccion6;

public class PruebaCasos3y4 {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria("A-101", 500.0);
        System.out.printf("Cuenta: %s | Saldo: %.2f%n", cuenta.getNumeroCuenta(), cuenta.getSaldo());

        Vehiculo v = new Vehiculo(200, true);
        v.imprimirEstado();
    }
}