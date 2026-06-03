package com.example.practicalab6.Repository;

import com.example.practicalab6.Entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {

    // Buscar por título (contiene, ignora mayúsculas/minúsculas)
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);

    // Buscar por género exacto
    List<Pelicula> findByGeneroIgnoreCase(String genero);

}
