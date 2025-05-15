# Employee Management System - Backend

Este es un proyecto backend desarrollado con **Spring Boot** para gestionar empleados y departamentos. Expone una API REST que puede ser consumida por un frontend como Angular.

## 📦 Tecnologías utilizadas

- Java 21
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Spring validation
- Hibernate
- H2 / MySQL (puedes configurar)
- Maven
- Lombok
- Mapstruct
- CORS habilitado para Angular (localhost:4200)

## 📁 Estructura del proyecto
com.hiberius.hiberius.

- config
- controllers
- dto
- exceptions
- mapper
- models
- repository
- services (clase DataInitializer: descomentar lienas 43 -> 63 ya tiene data quemada, si nos se descomenta la aplicacionaparece sin data)

### 🔹 Departamento (`/department`)

| Método | Endpoint                        | Descripción                                       |
|--------|----------------------------------|---------------------------------------------------|
| POST   | `/department/create`            | Crea un nuevo registro de departamento            |
| POST   | `/department/delete/{id}`       | Elimina lógicamente un departamento               |
| GET    | `/department`                   | Lista todos los departamentos existentes          |

---

### 🔹 Empleado (`/employee`)

| Método | Endpoint                                         | Descripción                                                         |
|--------|--------------------------------------------------|---------------------------------------------------------------------|
| POST   | `/employee/create/{departmentId}`               | Crea un nuevo empleado y lo asigna a un departamento                |
| POST   | `/employee/delete/{employeeId}`                 | Elimina lógicamente un empleado                                     |
| GET    | `/employee/highestSalary`                      | Devuelve un empleado con el salario más alto                        |
| GET    | `/employee/lowerAge`                           | Devuelve el empleado más joven                                      |
| GET    | `/employee/countLastMonth`                     | Número de empleados que ingresaron en el último mes                 |

---

## 🧪 Cómo probar con Postman

1. Asegúrate de tener el backend ejecutándose
2. Importa la colección Postman desde el siguiente archivo
   (src/main/resources/static/employeeManagement.postman_collection.json)

## ▶️ Pasos para la Ejecución
Requisitos Previos:
- Java 21
- Maven 3.9.9

### ⚙️ Pasos para la Ejecución del Proyecto Spring Boot

1. **Clonar el repositorio del backend**:
```bash
mvn clean install
mvn spring-boot:run
