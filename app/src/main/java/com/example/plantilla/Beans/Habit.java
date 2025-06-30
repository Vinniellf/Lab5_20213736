package com.example.plantilla.Beans;

import java.util.Date;

public class Habit {
    private String nombre;
    private String categoria;
    private Integer frecuencia;
    private Long fechaInicio;

    public Habit(String nombre, String categoria, Integer frecuencia, Long fechaInicio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
