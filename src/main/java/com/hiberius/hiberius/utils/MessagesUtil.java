package com.hiberius.hiberius.utils;

public class MessagesUtil {

    public static class Department {
        public static final String NOT_FOUND = "Departamento no encontrado con ID: %d";
        public static final String ALREADY_INACTIVE = "El departamento con ID %d ya está inactivo.";
        public static final String INVALID_ID = "El ID del departamento no puede ser nulo o menor o igual a cero.";
        public static final String ERROR_EXISTS = "Ya existe un departamento activo con el nombre: %s";
    }

    public static class Employee {
        public static final String ALREADY_INACTIVE = "El empleado con ID %d ya está inactivo.";
        public static final String ERROR_FUNDS = "No se encontro empleados activos";
        public static final String INVALID_ID = "El ID del empleado no puede ser nulo o menor o igual a cero.";
    }
}