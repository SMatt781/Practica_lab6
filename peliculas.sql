-- ============================================================
--  Script de inicialización - PracticaLab6
--  Se ejecuta automáticamente cuando MySQL corre en Docker:
--    -v peliculas.sql:/docker-entrypoint-initdb.d/init.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS practicalab6;
USE practicalab6;

CREATE TABLE IF NOT EXISTS rol (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id     INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email  VARCHAR(100) NOT NULL UNIQUE,
    pwd    VARCHAR(255) NOT NULL,
    activo TINYINT(1)   NOT NULL DEFAULT 1,
    rol_id INT          NOT NULL,
    FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE IF NOT EXISTS pelicula (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    titulo   VARCHAR(200) NOT NULL,
    director VARCHAR(100) NOT NULL,
    genero   VARCHAR(50)  NOT NULL,
    anio     INT          NOT NULL,
    duracion INT          NOT NULL
);

-- Datos de prueba (solo si la tabla está vacía)
INSERT INTO pelicula (titulo, director, genero, anio, duracion)
SELECT * FROM (SELECT 'El Padrino','Francis Ford Coppola','Drama',1972,175) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM pelicula LIMIT 1);

INSERT INTO pelicula (titulo, director, genero, anio, duracion) VALUES
    ('Inception',       'Christopher Nolan', 'Ciencia Ficción', 2010, 148),
    ('Interstellar',    'Christopher Nolan', 'Ciencia Ficción', 2014, 169),
    ('The Dark Knight', 'Christopher Nolan', 'Acción',          2008, 152),
    ('Parasite',        'Bong Joon-ho',      'Thriller',        2019, 132)
ON DUPLICATE KEY UPDATE titulo = titulo;

-- Nota: roles y usuarios los crea DataLoader.java al arrancar Spring Boot
-- usando BCryptPasswordEncoder (no se pueden insertar hashes directamente)
