-- ============================================================
--  Script: PracticaLab6 - Gestión de Películas
--  Ejecutar en MySQL: source peliculas.sql;
--
--  Usuarios creados automáticamente por DataLoader.java:
--    admin@cine.com  /  admin123  (rol: admin)
--    user@cine.com   /  user123   (rol: user)
-- ============================================================

CREATE DATABASE IF NOT EXISTS practicalab6;
USE practicalab6;

-- -------------------------------------------------------
-- Roles
-- -------------------------------------------------------
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;
DROP TABLE IF EXISTS pelicula;

CREATE TABLE rol (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

-- -------------------------------------------------------
-- Usuarios (pwd generado con BCrypt en DataLoader.java)
-- -------------------------------------------------------
CREATE TABLE usuario (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100)  NOT NULL,
    email  VARCHAR(100)  NOT NULL UNIQUE,
    pwd    VARCHAR(255)  NOT NULL,
    activo TINYINT(1)    NOT NULL DEFAULT 1,
    rol_id INT           NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

-- -------------------------------------------------------
-- Películas
-- -------------------------------------------------------
CREATE TABLE pelicula (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    titulo   VARCHAR(200) NOT NULL,
    director VARCHAR(100) NOT NULL,
    genero   VARCHAR(50)  NOT NULL,
    anio     INT          NOT NULL,
    duracion INT          NOT NULL
);

-- Datos de prueba para películas
INSERT INTO pelicula (titulo, director, genero, anio, duracion) VALUES
    ('El Padrino',      'Francis Ford Coppola', 'Drama',          1972, 175),
    ('Inception',       'Christopher Nolan',    'Ciencia Ficción',2010, 148),
    ('Interstellar',    'Christopher Nolan',    'Ciencia Ficción',2014, 169),
    ('Parasite',        'Bong Joon-ho',         'Thriller',       2019, 132),
    ('Toy Story',       'John Lasseter',        'Animación',      1995,  81),
    ('The Dark Knight', 'Christopher Nolan',    'Acción',         2008, 152);

-- Nota: las tablas de Spring Session (SPRING_SESSION, SPRING_SESSION_ATTRIBUTES)
-- se crean automáticamente gracias a: spring.session.jdbc.initialize-schema=always
