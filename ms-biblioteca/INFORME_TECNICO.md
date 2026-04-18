# Informe Técnico — ms-biblioteca
## Sistema de Gestión de Biblioteca Universitaria

**Institución:** IDAT  
**Fecha:** 16 de abril de 2026 (actualizado)  
**Tecnología principal:** Spring Boot 3.5.7 — Java 21

---

## 1. Descripción General

`ms-biblioteca` es un microservicio REST desarrollado con Spring Boot que gestiona el préstamo de libros en una biblioteca universitaria. Permite registrar libros, usuarios, préstamos y devoluciones, con autenticación segura mediante JWT y control de acceso basado en roles.

---

## 2. Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.7 | Framework base |
| Spring Data JPA | (incluido en Boot) | Persistencia con repositorios |
| Hibernate | (incluido en Boot) | ORM / mapeo entidades |
| Spring Security | (incluido en Boot) | Autenticación y autorización |
| PostgreSQL | (runtime) | Base de datos relacional |
| JJWT | 0.12.6 | Generación y validación de tokens JWT |
| MapStruct | 1.6.3 | Mapeo DTO ↔ Entidad |
| Lombok | (incluido en Boot) | Reducción de código boilerplate |
| SpringDoc OpenAPI | 2.8.6 | Documentación Swagger automática |
| Spring DevTools | (incluido en Boot) | Recarga en caliente en desarrollo |

---

## 3. Arquitectura del Proyecto

```
ms-biblioteca/
├── config/              → Configuración transversal (SwaggerConfig)
├── controller/          → Endpoints REST (AuthController, LibroController,
│                          PrestamoController, RolController, UsuarioController)
├── dto/
│   ├── request/         → DTOs de entrada con validaciones (@Valid)
│   └── response/        → DTOs de salida + ApiResponse genérico
├── entity/              → Entidades JPA (Libro, Usuario, Prestamo, Rol)
├── exception/           → Manejo centralizado de errores (GlobalExceptionHandler)
├── mapper/              → Interfaces MapStruct para conversión DTO ↔ Entidad
├── repository/          → Interfaces Spring Data JPA
├── security/            → JWT (JwtService, JwtAuthenticationFilter, SecurityConfig)
└── service/
    └── impl/            → Lógica de negocio (LibroServiceImpl, PrestamoServiceImpl, etc.)
```

---

## 4. Diseño de Base de Datos

**Motor:** PostgreSQL  
**Schema:** `bd_biblioteca`  
**Base de datos:** `BD_GESTION_BIBLIOTECA`  
**Puerto:** 5432

### 4.1 Entidades y Tablas

#### `tm_rol`
| Columna | Tipo | Descripción |
|---|---|---|
| NIDROL | int8 (PK) | Identificador único del rol |
| SNOMBRE | VARCHAR(20) | Nombre del rol (ADMIN, USUARIO) |
| SDESCRIPCION | VARCHAR(100) | Descripción del rol |
| NESTADO | int4 | 1=Activo, 0=Inactivo |

#### `tm_libro`
| Columna | Tipo | Descripción |
|---|---|---|
| NIDLIBRO | int8 (PK) | Identificador único |
| STITULO | VARCHAR(150) | Título del libro |
| SAUTOR | VARCHAR(100) | Autor |
| SISBN | VARCHAR(17) | ISBN (único) |
| SGENERO | VARCHAR(50) | Género literario |
| NANIOPUBLICACION | int4 | Año de publicación |
| NCANTIDADTOTAL | int4 | Total de ejemplares |
| NCANTIDADDISPONIBLE | int4 | Ejemplares disponibles |
| NESTADO | int4 | 1=Activo, 0=Inactivo |

#### `tm_usuario`
| Columna | Tipo | Descripción |
|---|---|---|
| NIDUSUARIO | int8 (PK) | Identificador único |
| SUSERNAME | VARCHAR(50) | Username para login (único) |
| SPASSWORD | VARCHAR(255) | Contraseña encriptada BCrypt |
| SNOMBRES | VARCHAR(100) | Nombres |
| SAPELLIDOS | VARCHAR(100) | Apellidos |
| SEMAIL | VARCHAR(100) | Correo electrónico (único) |
| NESTADO | int4 | 1=Activo, 0=Inactivo |

#### `tm_usuario_rol` (tabla relacional)
| Columna | Tipo | Descripción |
|---|---|---|
| ID_USUARIO | int8 (FK) | Referencia a tm_usuario |
| ID_ROL | int8 (FK) | Referencia a tm_rol |

#### `tm_prestamo`
| Columna | Tipo | Descripción |
|---|---|---|
| NIDPRESTAMO | int8 (PK) | Identificador único |
| ID_USUARIO | int8 (FK) | Usuario que realiza el préstamo |
| ID_LIBRO | int8 (FK) | Libro prestado |
| DFECHAPRESTAMO | DATE | Fecha del préstamo |
| DFECHADEVOLUCIONESPERADA | DATE | Fecha esperada de devolución |
| DFECHADEVOLUCIONREAL | DATE | Fecha real de devolución (null si pendiente) |
| NESTADO | int4 | 1=Activo, 0=Devuelto |

### 4.2 Relaciones entre Entidades

```
Rol ←──────────── [tm_usuario_rol] ────────────→ Usuario
                  (MUCHOS A MUCHOS)

Usuario ──────────────────────────────────────→ Prestamo
              (UNO A MUCHOS)

Libro ────────────────────────────────────────→ Prestamo
              (UNO A MUCHOS)
```

---

## 5. Seguridad

### 5.1 Flujo de Autenticación JWT

```
Cliente → POST /auth/login → AuthController
       → AuthenticationManager valida credenciales
       → JwtService genera token firmado (HMAC-SHA256)
       → Respuesta: { token, username, roles }

Cliente → GET /libros (Bearer token) → JwtAuthenticationFilter
        → Extrae y valida token
        → Carga usuario en SecurityContext
        → Spring Security verifica @PreAuthorize
```

### 5.2 Configuración JWT

| Parámetro | Valor |
|---|---|
| Algoritmo | HMAC-SHA256 |
| Expiración | 86400000 ms (24 horas) |
| Sesión | STATELESS (sin sesión en servidor) |

### 5.3 Roles y Permisos

| Endpoint | ADMIN | USUARIO |
|---|:---:|:---:|
| `POST /auth/login` | ✅ Público | ✅ Público |
| `POST /auth/register` | ✅ Público | ✅ Público |
| `GET /libros` | ✅ | ✅ |
| `GET /libros/{id}` | ✅ | ✅ |
| `GET /libros/buscar/titulo` | ✅ | ✅ |
| `GET /libros/buscar/autor` | ✅ | ✅ |
| `POST /libros` | ✅ | ❌ |
| `PUT /libros/{id}` | ✅ | ❌ |
| `DELETE /libros/{id}` | ✅ | ❌ |
| `GET /prestamos` | ✅ | ❌ |
| `GET /prestamos/{id}` | ✅ | ✅ |
| `GET /prestamos/historial/{idUsuario}` | ✅ | ✅ |
| `POST /prestamos` | ✅ | ❌ |
| `PUT /prestamos/{id}/devolucion` | ✅ | ❌ |

---

## 6. Endpoints REST

### Auth — `/api/v1/auth`
| Método | Ruta | Descripción |
|---|---|---|
| POST | `/auth/login` | Login, retorna token JWT |
| POST | `/auth/register` | Registra nuevo usuario |

### Libros — `/api/v1/libros`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/libros` | Lista todos los libros activos |
| GET | `/libros/{idLibro}` | Busca libro por ID |
| GET | `/libros/buscar/titulo?titulo=` | Búsqueda por título |
| GET | `/libros/buscar/autor?autor=` | Búsqueda por autor |
| POST | `/libros` | Crea nuevo libro (ADMIN) |
| PUT | `/libros/{idLibro}` | Actualiza libro (ADMIN) |
| DELETE | `/libros/{idLibro}` | Elimina libro lógico (ADMIN) |

### Préstamos — `/api/v1/prestamos`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/prestamos` | Lista todos los préstamos (ADMIN) |
| GET | `/prestamos/{idPrestamo}` | Busca préstamo por ID |
| GET | `/prestamos/historial/{idUsuario}` | Historial de usuario |
| POST | `/prestamos` | Registra nuevo préstamo (ADMIN) |
| PUT | `/prestamos/{idPrestamo}/devolucion` | Registra devolución (ADMIN) |

### Usuarios — `/api/v1/usuarios`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/usuarios` | Lista usuarios |
| GET | `/usuarios/{idUsuario}` | Busca usuario por ID |

### Roles — `/api/v1/roles`
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/roles` | Lista roles |
| GET | `/roles/{idRol}` | Busca rol por ID |
| POST | `/roles` | Crea rol (ADMIN) |
| PUT | `/roles/{idRol}` | Actualiza rol (ADMIN) |
| DELETE | `/roles/{idRol}` | Elimina rol (ADMIN) |

---

## 7. Validación de Datos

Todos los DTOs de entrada usan anotaciones de `jakarta.validation`:

| Anotación | Uso |
|---|---|
| `@NotBlank` | Campos de texto obligatorios |
| `@NotNull` | Campos numéricos/fecha obligatorios |
| `@Size(max = n)` | Límite de longitud en texto |
| `@Email` | Formato de correo electrónico |
| `@Positive` | Números enteros positivos |
| `@Future` | Fechas futuras (fecha devolución esperada) |

---

## 8. Manejo Centralizado de Errores

`GlobalExceptionHandler` con `@RestControllerAdvice` y `@Slf4j` captura y responde:

| Excepción | HTTP | Descripción |
|---|---|---|
| `MethodArgumentNotValidException` | 400 | Errores de validación por campo (detalle por cada campo) |
| `ConstraintViolationException` | 400 | Parámetros de ruta/query inválidos |
| `RuntimeException` | 400 | Errores de lógica de negocio |
| `BadCredentialsException` | 401 | Usuario o contraseña incorrectos |
| `ExpiredJwtException` | 401 | Token JWT expirado |
| `AccessDeniedException` | 403 | Sin permisos para la operación |
| `UsernameNotFoundException` | 404 | Usuario no encontrado |

**Formato estándar de respuesta (`ApiResponse<T>`):**
```json
{
  "success": true,
  "message": "Libro registrado exitosamente",
  "data": { ... }
}
```

**Ejemplo de error de validación:**
```json
{
  "success": false,
  "message": "Error de validación: titulo: El título del libro es obligatorio, isbn: El ISBN es obligatorio"
}
```

---

## 9. Documentación

### Postman
- Archivo: `ms-biblioteca.postman_collection.json`
- Incluye todos los endpoints organizados por carpetas: Auth, Roles, Usuarios, Libros, Préstamos
- Script automático que guarda el token JWT tras el login en la variable `{{token}}`
- Variable `{{baseUrl}}` configurada a `http://localhost:8081/api/v1`

### Swagger / OpenAPI
- Clase dedicada: `config/SwaggerConfig.java`
- URL: `http://localhost:8081/api/v1/swagger-ui.html`
- JSON OpenAPI: `http://localhost:8081/api/v1/v3/api-docs`
- Acceso público (sin token requerido)
- Incluye botón **Authorize** para ingresar el token JWT Bearer directamente en la UI
- Título: *API - Gestión de Biblioteca Universitaria v1.0*

---

## 10. Configuración de Ejecución

### Requisitos previos
- Java 21 instalado
- PostgreSQL activo en puerto 5432
- Base de datos `BD_GESTION_BIBLIOTECA` creada
- Script `database.sql` ejecutado en pgAdmin o psql

### Parámetros `application.yaml`
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

### Comando de ejecución
```bash
./mvnw spring-boot:run
```

---

## 11. Datos Iniciales

Al ejecutar `database.sql` se insertan automáticamente:

**Roles:**
- `ADMIN` — Administrador del sistema con acceso total
- `USUARIO` — Usuario regular con acceso de consulta y préstamos

**Libros de muestra (5):**
- Clean Code — Robert C. Martin
- The Pragmatic Programmer — Andrew Hunt
- Spring Boot in Action — Craig Walls
- Diseño de Sistemas — Carlos Araneda
- Java: The Complete Reference — Herbert Schildt

**Usuarios:** Se crean desde Postman mediante `POST /api/v1/auth/register`

---

## 12. Conclusión

El proyecto `ms-biblioteca` implementa de forma completa todos los requisitos solicitados:

- ✅ CRUD de libros, usuarios, préstamos y devoluciones
- ✅ Persistencia con JPA, Hibernate y Spring Data JPA sobre PostgreSQL
- ✅ Autenticación sin estado con JWT (HMAC-SHA256, 24h de expiración)
- ✅ Control de acceso por roles (ADMIN / USUARIO) con `@PreAuthorize`
- ✅ Validación de entrada en todos los DTOs
- ✅ Manejo centralizado de errores con 7 handlers y respuestas estandarizadas
- ✅ `SwaggerConfig` dedicado con autenticación Bearer JWT integrada en la UI
- ✅ Documentación con Postman Collection y Swagger UI
