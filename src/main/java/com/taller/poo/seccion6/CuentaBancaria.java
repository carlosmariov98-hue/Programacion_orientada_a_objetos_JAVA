package com.taller.poo.seccion6;

public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;

    // ERROR ORIGINAL: "public void CuentaBancaria(...)" era un método, no un constructor.
    // CORRECCIÓN: un constructor no lleva tipo de retorno.
    public CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
}