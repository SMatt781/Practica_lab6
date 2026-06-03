package com.example.practicalab6.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede superar los 200 caracteres")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "El director es obligatorio")
    @Size(max = 100, message = "El director no puede superar los 100 caracteres")
    @Column(name = "director")
    private String director;

    @NotBlank(message = "El género es obligatorio")
    @Column(name = "genero")
    private String genero;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    @Max(value = 2100, message = "El año no puede ser tan lejano")
    @Column(name = "anio")
    private Integer anio;

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser un número positivo")
    @Column(name = "duracion")
    private Integer duracion;

    // ------------------------------------------------
    // Getters y Setters (sin Lombok para evitar
    // problemas de annotation processing en Maven)
    // ------------------------------------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

}
