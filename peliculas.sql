-- ============================================================
--  Script de base de datos: PracticaLab6 - Gestión de Películas
--  Ejecutar en MySQL antes de correr la aplicación:
--    source peliculas.sql;
-- ============================================================

CREATE DATABASE IF NOT EXISTS practicalab6;
USE practicalab6;

DROP TABLE IF EXISTS pelicula;

CREATE TABLE pelicula (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    titulo   VARCHAR(200) NOT NULL,
    director VARCHAR(100) NOT NULL,
    genero   VARCHAR(50)  NOT NULL,
    anio     INT          NOT NULL,
    duracion INT          NOT NULL
);

-- Datos de prueba
INSERT INTO pelicula (titulo, director, genero, anio, duracion) VALUES
    ('El Padrino',     'Francis Ford Coppola', 'Drama',          1972, 175),
    ('Inception',      'Christopher Nolan',    'Ciencia Ficción',2010, 148),
    ('Interstellar',   'Christopher Nolan',    'Ciencia Ficción',2014, 169),
    ('Parasite',       'Bong Joon-ho',         'Thriller',       2019, 132),
    ('Toy Story',      'John Lasseter',        'Animación',      1995,  81),
    ('The Dark Knight','Christopher Nolan',    'Acción',         2008, 152);
