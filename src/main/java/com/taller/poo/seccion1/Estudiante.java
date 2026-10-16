package com.taller.poo.seccion1;

import java.util.Locale;

public class Estudiante {

    private String identificacion;
    private String nombreCompleto;
    private double promedioCalificaciones;
    private boolean estaMatriculado;

    public Estudiante() {
    }

    public Estudiante(String identificacion, String nombreCompleto, double promedioCalificaciones, boolean estaMatriculado) {
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.promedioCalificaciones = promedioCalificaciones;
        this.estaMatriculado = estaMatriculado;
    }

    public String obtenerReporteAcademicos() {
        StringBuilder reporte = new StringBuilder();

        reporte.append(String.format(Locale.US, "ID: %s%n", this.identificacion));
        reporte.append(String.format(Locale.US, "Nombre: %s%n", this.nombreCompleto));
        reporte.append(String.format(Locale.US, "Promedio: %.2f%n", this.promedioCalificaciones));
        reporte.append(String.format(Locale.US, "Matriculado: %b", this.estaMatriculado));

        return reporte.toString();
    }

    // ---------- Get y set----------
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public double getPromedioCalificaciones() {
        return promedioCalificaciones;
    }

    public void setPromedioCalificaciones(double promedioCalificaciones) {
        this.promedioCalificaciones = promedioCalificaciones;
    }

    public boolean isEstaMatriculado() {
        return estaMatriculado;
    }

    public void setEstaMatriculado(boolean estaMatriculado) {
        this.estaMatriculado = estaMatriculado;
    }
}
