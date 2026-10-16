package com.taller.poo.seccion6;

import com.taller.poo.seccion1.Estudiante;

public class PruebaEstudiante {
    public static void main(String[] args) {
        // CORRECCIÓN: instanciar el objeto con new antes de invocar el método
        Estudiante estudiante1 = new Estudiante("E001", "Laura Mejía", 4.35, true);

        String reporte = estudiante1.obtenerReporteAcademicos();
        System.out.println(reporte);
    }
}