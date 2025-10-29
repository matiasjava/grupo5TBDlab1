package com.tbd.lab1tbd.dto;

public class SitioTuristico {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Double calificacionPromedio;
    private Integer totalReseñas;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }
    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    public Integer getTotalReseñas() {
        return totalReseñas;
    }
    public void setTotalReseñas(Integer totalReseñas) {
        this.totalReseñas = totalReseñas;
    }
}