# ms-biblioteca

Sistema de Gestión de Biblioteca Universitaria — Microservicio REST desarrollado con **Spring Boot 3.5.7** y **Java 21**.

## Descripción

API REST para gestionar el préstamo de libros en una biblioteca universitaria. Permite registrar libros, usuarios, préstamos y devoluciones, con autenticación segura mediante **JWT** y control de acceso basado en roles (ADMIN / USUARIO).

---

## Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.7 |
| Spring Security | 6.x |
| PostgreSQL | 15+ |
| JJWT | 0.12.6 |
| MapStruct | 1.6.3 |
| SpringDoc OpenAPI | 2.8.6 |

---

## Requisitos Previos

- Java 21
- PostgreSQL activo en el puerto `5432`
- Base de datos `BD_GESTION_BIBLIOTECA` creada
- Script `database.sql` ejecutado

---

## Configuración

El archivo `src/main/resources/application.yaml` contiene los parámetros de conexión:

```yaml
server:
  port: 8081
  servlet:
    context-path: /api/v1

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/BD_GESTION_BIBLIOTECA?currentSchema=bd_biblioteca
    username: postgres
    password: ****
```

---

## Ejecución

```bash
./mvnw spring-boot:run
```

La API quedará disponible en: `http://localhost:8081/api/v1`

---

## Base de Datos

Ejecutar el script `database.sql` en pgAdmin o psql. Crea las tablas:

- `tm_rol` — Roles del sistema
- `tm_libro` — Catálogo de libros
- `tm_usuario` — Usuarios registrados
- `tm_prestamo` — Registro de préstamos
- `tm_usuario_rol` — Relación usuario ↔ rol

Incluye datos iniciales: 2 roles (`ADMIN`, `USUARIO`) y 5 libros de muestra.

---

## Endpoints Principales

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| POST | `/auth/register` | Registrar usuario | Público |
| POST | `/auth/login` | Obtener token JWT | Público |
| GET | `/libros` | Listar libros | ADMIN / USUARIO |
| POST | `/libros` | Registrar libro | ADMIN |
| PUT | `/libros/{id}` | Actualizar libro | ADMIN |
| DELETE | `/libros/{id}` | Eliminar libro | ADMIN |
| GET | `/prestamos` | Listar préstamos | ADMIN |
| POST | `/prestamos` | Registrar préstamo | ADMIN / USUARIO |
| PUT | `/prestamos/{id}/devolver` | Registrar devolución | ADMIN / USUARIO |

---

## Seguridad

- Autenticación **sin estado (STATELESS)** con JWT (HMAC-SHA256, 24h de expiración)
- Filtro `JwtAuthenticationFilter` valida el token en cada request
- Control de acceso con `@PreAuthorize` en cada endpoint
- Contraseñas almacenadas con **BCrypt**

---

## Documentación

### Swagger UI
Disponible en: `http://localhost:8081/api/v1/swagger-ui.html`

Incluye botón **Authorize** para ingresar el token JWT Bearer directamente en la UI.

### Postman
Importar el archivo `ms-biblioteca.postman_collection.json`. Incluye:
- Todos los endpoints organizados por carpetas
- Script automático que guarda el token JWT tras el login en la variable `{{token}}`

---

## Estructura del Proyecto

```
src/main/java/pe/edu/idat/biblioteca/
├── config/         → SwaggerConfig (autenticación Bearer JWT en UI)
├── controller/     → AuthController, LibroController, PrestamoController, RolController, UsuarioController
├── dto/
│   ├── request/    → DTOs de entrada con validaciones
│   └── response/   → DTOs de salida + ApiResponse genérico
├── entity/         → Libro, Usuario, Prestamo, Rol
├── exception/      → GlobalExceptionHandler (7 handlers)
├── mapper/         → Interfaces MapStruct
├── repository/     → Interfaces Spring Data JPA
├── security/       → JwtService, JwtAuthenticationFilter, SecurityConfig
└── service/
    └── impl/       → Lógica de negocio
```

---

## Institución

**IDAT** — Ingeniería de Sistemas  
Curso: Desarrollo de Aplicaciones con Microservicios
