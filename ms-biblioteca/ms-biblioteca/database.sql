-- =============================================================
--  Ms-Biblioteca  |  Script de inicialización de base de datos
--  Ejecutar en pgAdmin o psql con superusuario (postgres)
-- =============================================================

-- 1. Crear schema
CREATE SCHEMA IF NOT EXISTS bd_biblioteca;

SET search_path TO bd_biblioteca;

-- =============================================================
--  SECUENCIAS
-- =============================================================

CREATE SEQUENCE IF NOT EXISTS seq_tm_rol
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS seq_tm_libro
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS seq_tm_usuario
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE SEQUENCE IF NOT EXISTS seq_tm_prestamo
    START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

-- =============================================================
--  TABLAS  (JPA las crea automáticamente con ddl-auto: update,
--           pero se incluyen aquí como referencia)
-- =============================================================

CREATE TABLE IF NOT EXISTS tm_rol (
    id_rol      BIGINT       DEFAULT nextval('seq_tm_rol') PRIMARY KEY,
    nombre      VARCHAR(20)  NOT NULL UNIQUE,
    descripcion VARCHAR(100),
    estado      INTEGER      NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS tm_libro (
    id_libro              BIGINT       DEFAULT nextval('seq_tm_libro') PRIMARY KEY,
    titulo                VARCHAR(150) NOT NULL,
    autor                 VARCHAR(100) NOT NULL,
    isbn                  VARCHAR(17)  NOT NULL UNIQUE,
    genero                VARCHAR(50),
    anio_publicacion      INTEGER,
    cantidad_total        INTEGER      NOT NULL,
    cantidad_disponible   INTEGER      NOT NULL,
    estado                INTEGER      NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS tm_usuario (
    id_usuario  BIGINT       DEFAULT nextval('seq_tm_usuario') PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    nombres     VARCHAR(100) NOT NULL,
    apellidos   VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    estado      INTEGER      NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS tm_usuario_rol (
    id_usuario  BIGINT NOT NULL REFERENCES tm_usuario(id_usuario),
    id_rol      BIGINT NOT NULL REFERENCES tm_rol(id_rol),
    PRIMARY KEY (id_usuario, id_rol)
);

CREATE TABLE IF NOT EXISTS tm_prestamo (
    id_prestamo               BIGINT  DEFAULT nextval('seq_tm_prestamo') PRIMARY KEY,
    id_usuario                BIGINT  NOT NULL REFERENCES tm_usuario(id_usuario),
    id_libro                  BIGINT  NOT NULL REFERENCES tm_libro(id_libro),
    fecha_prestamo            DATE    NOT NULL,
    fecha_devolucion_esperada DATE    NOT NULL,
    fecha_devolucion_real     DATE,
    estado                    INTEGER NOT NULL DEFAULT 1
);

-- =============================================================
--  DATOS INICIALES
-- =============================================================

-- Roles del sistema
INSERT INTO tm_rol (nombre, descripcion)
VALUES
    ('ADMIN',   'Administrador del sistema con acceso total'),
    ('USUARIO', 'Usuario regular con acceso de consulta y préstamos')
ON CONFLICT (nombre) DO NOTHING;

-- =============================================================
--  FIN DEL SCRIPT
--  Credenciales en application.yaml:
--    username: postgres
--    password: 12345
-- =============================================================
