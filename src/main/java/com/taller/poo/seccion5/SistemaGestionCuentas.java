package com.taller.poo.seccion5;

import com.taller.poo.seccion1.CuentaBancaria;
import com.taller.poo.seccion2.ConversorMoneda;

import java.util.Locale;
import java.util.Scanner;

public class SistemaGestionCuentas {

    static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        cuentas[0] = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.00);
        cuentas[1] = new CuentaBancaria("1002", "Ana Gómez");
        cuentas[2] = new CuentaBancaria("1003", "Luis Torres", 300.00, 200.00);

        int opcion;

        do {
            imprimirMenu();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    depositarEnCuenta(cuentas);
                    break;
                case 2:
                    retirarDeCuenta(cuentas);
                    break;
                case 3:
                    mostrarReporte(cuentas);
                    break;
                case 4:
                    System.out.printf("%nSistema finalizado. ¡Hasta pronto!%n");
                    break;
                default:
                    System.out.printf("Error: la opción %d no es válida (use 1 a 4).%n", opcion);
            }
        } while (opcion != 4);
    }

    static void imprimirMenu() {
        System.out.printf("%n===== SISTEMA DE GESTIÓN DE CUENTAS =====%n");
        System.out.printf("1. Depositar en una cuenta%n");
        System.out.printf("2. Retirar de una cuenta%n");
        System.out.printf("3. Ver reporte consolidado%n");
        System.out.printf("4. Salir%n");
    }

    static void depositarEnCuenta(CuentaBancaria[] cuentas) {
        System.out.printf("Número de cuenta: ");
        String numero = teclado.nextLine().trim();

        CuentaBancaria cuenta = buscarCuenta(cuentas, numero);
        if (cuenta == null) {
            System.out.printf("Error: la cuenta %s no existe.%n", numero);
            return;
        }

        double monto = leerDecimal("Monto a depositar: ");
        if (monto <= 0) {
            System.out.printf("Error: el monto debe ser mayor a cero (ingresó %.2f).%n", monto);
        } else if (cuenta.depositar(monto)) {
            System.out.printf("Depósito exitoso. Nuevo saldo de %s: %s%n",
                    cuenta.getTitular(), ConversorMoneda.formatearMoneda(cuenta.getSaldo()));
        } else {
            System.out.printf("Error: no se pudo realizar el depósito.%n");
        }
    }

    static void retirarDeCuenta(CuentaBancaria[] cuentas) {
        System.out.printf("Número de cuenta: ");
        String numero = teclado.nextLine().trim();

        CuentaBancaria cuenta = buscarCuenta(cuentas, numero);
        if (cuenta == null) {
            System.out.printf("Error: la cuenta %s no existe.%n", numero);
            return;
        }

        double monto = leerDecimal("Monto a retirar: ");
        if (monto <= 0) {
            System.out.printf("Error: el monto debe ser mayor a cero (ingresó %.2f).%n", monto);
        } else if (cuenta.retirar(monto)) {
            System.out.printf("Retiro exitoso. Nuevo saldo de %s: %s%n",
                    cuenta.getTitular(), ConversorMoneda.formatearMoneda(cuenta.getSaldo()));
        } else {
            System.out.printf("Error: fondos insuficientes (saldo %s + crédito %s).%n",
                    ConversorMoneda.formatearMoneda(cuenta.getSaldo()),
                    ConversorMoneda.formatearMoneda(cuenta.getLimiteCredito()));
        }
    }

    static void mostrarReporte(CuentaBancaria[] cuentas) {
        StringBuilder detalle = new StringBuilder();
        double saldoTotal = 0.0;

        for (int i = 0; i < cuentas.length; i++) {
            CuentaBancaria c = cuentas[i];
            detalle.append(String.format("[%d] Cuenta: %s | Titular: %s | Saldo: %s | Crédito: %s%n",
                    i + 1, c.getNumeroCuenta(), c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito())));
            saldoTotal += c.getSaldo();
        }

        System.out.printf("%n=== REPORTE CONSOLIDADO ===%n");
        System.out.printf("%s", detalle);
        System.out.printf("Cuentas registradas: %d%n", cuentas.length);
        System.out.printf("Saldo total acumulado: %s%n", ConversorMoneda.formatearMoneda(saldoTotal));
    }

    // Recorre el arreglo y retorna la cuenta con ese número, o null si no existe
    static CuentaBancaria buscarCuenta(CuentaBancaria[] cuentas, String numeroCuenta) {
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(numeroCuenta)) {
                return cuentas[i];
            }
        }
        return null;
    }

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.printf("%s", mensaje);
            String texto = teclado.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" no es un número entero.%n", texto);
            }
        }
    }

    static double leerDecimal(String mensaje) {
        while (true) {
            System.out.printf("%s", mensaje);
            String texto = teclado.nextLine().trim();
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" no es un número válido (use punto decimal).%n", texto);
            }
        }
    }
}