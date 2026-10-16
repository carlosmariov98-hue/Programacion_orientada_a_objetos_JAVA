package com.taller.poo.seccion4;

import com.taller.poo.seccion1.CuentaBancaria;
import com.taller.poo.seccion1.Estudiante;
import com.taller.poo.seccion1.Vehiculo;
import com.taller.poo.seccion2.ConversorMoneda;

import java.util.Locale;

public class Principal {

    public static void main(String[] args) {
        // Fuerza el punto decimal (1250.75) sin importar la configuración regional
        Locale.setDefault(Locale.US);

        // ---------- Vehículos (1 por defecto y 2 parametrizados) ----------
        Vehiculo[] vehiculos = {
                new Vehiculo("Toyota", "Corolla", 15000.50, 180, true),
                new Vehiculo(),
                new Vehiculo("Renault", "Duster", 42300.75, 170, false)
        };

        // ---------- Estudiantes ----------
        Estudiante estudiante1 = new Estudiante("E001", "Laura Mejía", 4.35, true);
        Estudiante estudiante2 = new Estudiante("E002", "Andrés Rojas", 3.80, true);
        Estudiante estudiante3 = new Estudiante("E003", "Camila Torres", 2.95, false);
        Estudiante[] estudiantes = {estudiante1, estudiante2, estudiante3};

        // ---------- Cuentas (usando los 3 constructores) ----------
        CuentaBancaria cuenta1 = new CuentaBancaria("1001", "Carlos Pérez", 1000.00, 500.00);
        CuentaBancaria cuenta2 = new CuentaBancaria("1002", "Ana Gómez");
        CuentaBancaria cuenta3 = new CuentaBancaria();

        // ---------- Modificación de estado ----------
        boolean r1 = cuenta1.depositar(250.75);
        boolean r2 = cuenta2.depositar(100.00);
        boolean r3 = cuenta2.retirar(50.00);
        boolean r4 = cuenta2.retirar(500.00);   // supera saldo + crédito: debe fallar
        boolean r5 = cuenta3.depositar(-20.00); // monto inválido: debe fallar
        boolean r6 = cuenta3.retirar(10.00);    // sin saldo ni crédito: debe fallar

        System.out.printf("=== OPERACIONES REALIZADAS ===%n");
        System.out.printf("Depósito de 250.75 en cuenta 1001: %b%n", r1);
        System.out.printf("Depósito de 100.00 en cuenta 1002: %b%n", r2);
        System.out.printf("Retiro de 50.00 en cuenta 1002: %b%n", r3);
        System.out.printf("Retiro de 500.00 en cuenta 1002: %b%n", r4);
        System.out.printf("Depósito de -20.00 en cuenta 000000: %b%n", r5);
        System.out.printf("Retiro de 10.00 en cuenta 000000: %b%n", r6);

        // ---------- Reportes ----------
        System.out.printf("%n=== REPORTE DE VEHÍCULOS ===%n");
        for (int i = 0; i < vehiculos.length; i++) {
            Vehiculo v = vehiculos[i];
            System.out.printf("[%d] Marca: %s | Modelo: %s | KM: %.2f | VelMax: %d km/h | Encendido: %b%n",
                    i + 1, v.getMarca(), v.getModelo(), v.getKilometraje(),
                    v.getVelocidadMaxima(), v.isEncendido());
        }

        System.out.printf("%n=== REPORTE DE ESTUDIANTES ===%n");
        for (Estudiante e : estudiantes) {
            System.out.printf("%s%n%n", e.obtenerReporteAcademicos());
        }

        System.out.printf("=== REPORTE DE CUENTAS BANCARIAS ===%n");
        CuentaBancaria[] cuentas = {cuenta1, cuenta2, cuenta3};
        for (CuentaBancaria c : cuentas) {
            System.out.printf("Cuenta: %s | Titular: %s | Saldo Final: %s | Crédito: %s%n",
                    c.getNumeroCuenta(), c.getTitular(),
                    ConversorMoneda.formatearMoneda(c.getSaldo()),
                    ConversorMoneda.formatearMoneda(c.getLimiteCredito()));
        }

        // ---------- Métodos estáticos (sin instanciar) ----------
        System.out.printf("%n=== CONVERSOR DE MONEDA ===%n");
        double euros = ConversorMoneda.convertirDolaresAEuros(250.00, 0.92);
        double invalido = ConversorMoneda.convertirDolaresAEuros(-5.00, 0.92);
        System.out.printf("250.00 USD a tasa 0.92 = %s EUR%n", ConversorMoneda.formatearMoneda(euros));
        System.out.printf("Monto inválido (-5.00 USD) = %s EUR%n", ConversorMoneda.formatearMoneda(invalido));
    }
}