-- =============================================================
--  Ms-Biblioteca  |  Script de inicializacion de base de datos
--  Ejecutar en pgAdmin o psql con superusuario (postgres)
-- =============================================================

CREATE SCHEMA IF NOT EXISTS bd_biblioteca;

-- =============================================================
--  SECUENCIAS
-- =============================================================

CREATE SEQUENCE IF NOT EXISTS bd_biblioteca.seq_tm_rol
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS bd_biblioteca.seq_tm_libro
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS bd_biblioteca.seq_tm_usuario
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS bd_biblioteca.seq_tm_prestamo
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

-- =============================================================
--  TABLAS
-- =============================================================

CREATE TABLE bd_biblioteca.tm_rol
(
    NIDROL       int8         NOT NULL DEFAULT nextval('bd_biblioteca.seq_tm_rol'),
    SNOMBRE      VARCHAR(20)  NOT NULL,
    SDESCRIPCION VARCHAR(100),
    NESTADO      int4         NOT NULL DEFAULT 1,
    --Almacena los roles del sistema (ADMIN, USUARIO)
    CONSTRAINT pk_tm_rol PRIMARY KEY (NIDROL),
    CONSTRAINT uq_tm_rol_snombre UNIQUE (SNOMBRE)
);

comment on table  bd_biblioteca.tm_rol              is 'Tabla de roles del sistema';
comment on column bd_biblioteca.tm_rol.NIDROL       is 'Identificador unico del rol';
comment on column bd_biblioteca.tm_rol.SNOMBRE      is 'Nombre del rol (ADMIN, USUARIO)';
comment on column bd_biblioteca.tm_rol.SDESCRIPCION is 'Descripcion del rol';
comment on column bd_biblioteca.tm_rol.NESTADO      is '1=Activo, 0=Inactivo';

CREATE TABLE bd_biblioteca.tm_libro
(
    NIDLIBRO             int8         NOT NULL DEFAULT nextval('bd_biblioteca.seq_tm_libro'),
    STITULO              VARCHAR(150) NOT NULL,
    SAUTOR               VARCHAR(100) NOT NULL,
    SISBN                VARCHAR(17)  NOT NULL,
    SGENERO              VARCHAR(50),
    NANIOPUBLICACION     int4,
    NCANTIDADTOTAL       int4         NOT NULL,
    NCANTIDADDISPONIBLE  int4         NOT NULL,
    NESTADO              int4         NOT NULL DEFAULT 1,
    --Almacena el catalogo de libros disponibles para prestamo
    CONSTRAINT pk_tm_libro PRIMARY KEY (NIDLIBRO),
    CONSTRAINT uq_tm_libro_sisbn UNIQUE (SISBN)
);

comment on table  bd_biblioteca.tm_libro                     is 'Tabla de libros del catalogo';
comment on column bd_biblioteca.tm_libro.NIDLIBRO            is 'Identificador unico del libro';
comment on column bd_biblioteca.tm_libro.STITULO             is 'Titulo del libro';
comment on column bd_biblioteca.tm_libro.SAUTOR              is 'Autor del libro';
comment on column bd_biblioteca.tm_libro.SISBN               is 'Codigo ISBN del libro';
comment on column bd_biblioteca.tm_libro.SGENERO             is 'Genero literario';
comment on column bd_biblioteca.tm_libro.NANIOPUBLICACION    is 'Anio de publicacion';
comment on column bd_biblioteca.tm_libro.NCANTIDADTOTAL      is 'Cantidad total de ejemplares';
comment on column bd_biblioteca.tm_libro.NCANTIDADDISPONIBLE is 'Cantidad disponible para prestamo';
comment on column bd_biblioteca.tm_libro.NESTADO             is '1=Activo, 0=Inactivo';

CREATE TABLE bd_biblioteca.tm_usuario
(
    NIDUSUARIO int8         NOT NULL DEFAULT nextval('bd_biblioteca.seq_tm_usuario'),
    SUSERNAME  VARCHAR(50)  NOT NULL,
    SPASSWORD  VARCHAR(255) NOT NULL,
    SNOMBRES   VARCHAR(100) NOT NULL,
    SAPELLIDOS VARCHAR(100) NOT NULL,
    SEMAIL     VARCHAR(100) NOT NULL,
    NESTADO    int4         NOT NULL DEFAULT 1,
    --Almacena los usuarios que pueden acceder al sistema
    CONSTRAINT pk_tm_usuario          PRIMARY KEY (NIDUSUARIO),
    CONSTRAINT uq_tm_usuario_username UNIQUE (SUSERNAME),
    CONSTRAINT uq_tm_usuario_email    UNIQUE (SEMAIL)
);

comment on table  bd_biblioteca.tm_usuario           is 'Tabla de usuarios del sistema';
comment on column bd_biblioteca.tm_usuario.NIDUSUARIO is 'Identificador unico del usuario';
comment on column bd_biblioteca.tm_usuario.SUSERNAME  is 'Nombre de usuario para login';
comment on column bd_biblioteca.tm_usuario.SPASSWORD  is 'Contrasena encriptada (BCrypt)';
comment on column bd_biblioteca.tm_usuario.SNOMBRES   is 'Nombres del usuario';
comment on column bd_biblioteca.tm_usuario.SAPELLIDOS is 'Apellidos del usuario';
comment on column bd_biblioteca.tm_usuario.SEMAIL     is 'Correo electronico del usuario';
comment on column bd_biblioteca.tm_usuario.NESTADO    is '1=Activo, 0=Inactivo';

CREATE TABLE bd_biblioteca.tm_usuario_rol
(
    ID_USUARIO int8 NOT NULL,
    ID_ROL     int8 NOT NULL,
    CONSTRAINT pk_tm_usuario_rol     PRIMARY KEY (ID_USUARIO, ID_ROL),
    CONSTRAINT fk_usuariorol_usuario FOREIGN KEY (ID_USUARIO) REFERENCES bd_biblioteca.tm_usuario(NIDUSUARIO),
    CONSTRAINT fk_usuariorol_rol     FOREIGN KEY (ID_ROL)     REFERENCES bd_biblioteca.tm_rol(NIDROL)
);

comment on table  bd_biblioteca.tm_usuario_rol           is 'Tabla relacional usuario-rol (muchos a muchos)';
comment on column bd_biblioteca.tm_usuario_rol.ID_USUARIO is 'Referencia al usuario';
comment on column bd_biblioteca.tm_usuario_rol.ID_ROL     is 'Referencia al rol';

CREATE TABLE bd_biblioteca.tm_prestamo
(
    NIDPRESTAMO              int8 NOT NULL DEFAULT nextval('bd_biblioteca.seq_tm_prestamo'),
    ID_USUARIO               int8 NOT NULL,
    ID_LIBRO                 int8 NOT NULL,
    DFECHAPRESTAMO           DATE NOT NULL,
    DFECHADEVOLUCIONESPERADA DATE NOT NULL,
    DFECHADEVOLUCIONREAL     DATE,
    NESTADO                  int4 NOT NULL DEFAULT 1,
    --Registra los prestamos de libros realizados por los usuarios
    CONSTRAINT pk_tm_prestamo    PRIMARY KEY (NIDPRESTAMO),
    CONSTRAINT fk_prestamo_usuario FOREIGN KEY (ID_USUARIO) REFERENCES bd_biblioteca.tm_usuario(NIDUSUARIO),
    CONSTRAINT fk_prestamo_libro   FOREIGN KEY (ID_LIBRO)   REFERENCES bd_biblioteca.tm_libro(NIDLIBRO)
);

comment on table  bd_biblioteca.tm_prestamo                       is 'Tabla de prestamos de libros';
comment on column bd_biblioteca.tm_prestamo.NIDPRESTAMO              is 'Identificador unico del prestamo';
comment on column bd_biblioteca.tm_prestamo.ID_USUARIO               is 'Referencia al usuario que realiza el prestamo';
comment on column bd_biblioteca.tm_prestamo.ID_LIBRO                 is 'Referencia al libro prestado';
comment on column bd_biblioteca.tm_prestamo.DFECHAPRESTAMO           is 'Fecha en que se realizo el prestamo';
comment on column bd_biblioteca.tm_prestamo.DFECHADEVOLUCIONESPERADA is 'Fecha esperada de devolucion';
comment on column bd_biblioteca.tm_prestamo.DFECHADEVOLUCIONREAL     is 'Fecha real de devolucion (null si aun no devuelto)';
comment on column bd_biblioteca.tm_prestamo.NESTADO                  is '1=Activo, 0=Devuelto';

-- =============================================================
--  DATOS INICIALES
-- =============================================================

INSERT INTO bd_biblioteca.tm_rol (SNOMBRE, SDESCRIPCION, NESTADO)
VALUES
    ('ADMIN',   'Administrador del sistema con acceso total',         1),
    ('USUARIO', 'Usuario regular con acceso de consulta y prestamos', 1)
ON CONFLICT (SNOMBRE) DO NOTHING;

INSERT INTO bd_biblioteca.tm_libro (STITULO, SAUTOR, SISBN, SGENERO, NANIOPUBLICACION, NCANTIDADTOTAL, NCANTIDADDISPONIBLE, NESTADO)
VALUES
    ('Clean Code',                   'Robert C. Martin', '978-0132350884', 'Programacion', 2008, 5, 4, 1),
    ('The Pragmatic Programmer',     'Andrew Hunt',      '978-0201616224', 'Programacion', 1999, 3, 3, 1),
    ('Spring Boot in Action',        'Craig Walls',      '978-1617292545', 'Programacion', 2016, 4, 3, 1),
    ('Diseno de Sistemas',           'Carlos Araneda',   '978-9563400080', 'Sistemas',     2015, 2, 2, 1),
    ('Java: The Complete Reference', 'Herbert Schildt',  '978-1260440232', 'Programacion', 2019, 6, 5, 1)
ON CONFLICT (SISBN) DO NOTHING;

-- Los usuarios se crean desde Postman via POST /auth/register
-- POST http://localhost:8081/api/v1/auth/register
