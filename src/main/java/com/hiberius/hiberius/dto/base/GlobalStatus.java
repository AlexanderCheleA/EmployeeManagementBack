package com.hiberius.hiberius.dto.base;

public enum GlobalStatus {
    A("Activo"),
    I("Inactivo");

    private final String descripcion;

    GlobalStatus(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
