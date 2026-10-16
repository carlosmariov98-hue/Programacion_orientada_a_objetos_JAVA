package com.taller.poo.seccion1;

public class CuentaBancaria {

    // ===== SECCIÓN 1: atributos privados =====
    private String numeroCuenta;
    private String titular;
    private double saldo;
    private Double limiteCredito;

    /*
     * PREGUNTAS DE ANÁLISIS:
     * 1. ¿Qué diferencia ocurre a nivel de memoria y asignación por defecto al
     *    declarar 'saldo' como double primitivo frente a 'limiteCredito' como Wrapper Double?
     *
     * RESPUESTA 1:
     * 'saldo' (double primitivo) guarda el número directamente dentro del objeto,
     * ocupa 8 bytes y, si no se asigna, vale 0.0 por defecto.
     * 'limiteCredito' (Double) guarda solo una referencia; el valor vive en otro
     * objeto del Heap con su propia cabecera, por lo que consume más memoria.
     * Si no se asigna, su valor por defecto es null (no apunta a ningún objeto).
     *
     * 2. ¿Por qué una operación aritmética sobre un atributo no inicializado de
     *    tipo Double provoca una NullPointerException, mientras que sobre un double
     *    primitivo opera con 0.0?
     *
     * RESPUESTA 2:
     * Para operar, Java hace unboxing: llama internamente a doubleValue() sobre el
     * objeto Double. Si la referencia es null, no hay objeto sobre el cual llamar al
     * método y se lanza NullPointerException. En cambio, el double primitivo nunca
     * es null: tiene el valor 0.0 por defecto y se puede operar directamente.
     */

    public CuentaBancaria() {
        this.numeroCuenta = "000000";
        this.titular = "Sin Nombre";
        this.saldo = 0.0;
        this.limiteCredito = 0.0;
    }

    // Constructor de 2 parámetros
    public CuentaBancaria(String numeroCuenta, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteCredito = 100.0;
    }

    // Constructor completo de 4 parámetros
    public CuentaBancaria(String numeroCuenta, String titular, double saldo, Double limiteCredito) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
    }


    // Incrementa el saldo solo si el monto es estrictamente mayor a 0
    public boolean depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            return true;
        } else {
            return false;
        }
    }

    // Descuenta el monto si es positivo y el saldo (más el crédito, si existe) alcanza
    public boolean retirar(double monto) {
        double credito = (limiteCredito != null) ? limiteCredito : 0.0;

        if (monto > 0 && (saldo + credito) >= monto) {
            saldo -= monto;
            return true;
        } else {
            return false;
        }
    }

    // ===== Métodos de acceso =====
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public Double getLimiteCredito() { return limiteCredito; }
}