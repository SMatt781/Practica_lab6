package com.example.practicalab6.Service;

import com.example.practicalab6.Entity.Pelicula;
import com.example.practicalab6.Repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository peliculaRepository;

    public List<Pelicula> listarTodas() {
        return peliculaRepository.findAll();
    }

    public Pelicula buscarPorId(Integer id) {
        return peliculaRepository.findById(id).orElse(null);
    }

    public void guardar(Pelicula pelicula) {
        peliculaRepository.save(pelicula);
    }

    public void eliminar(Integer id) {
        peliculaRepository.deleteById(id);
    }

    public List<Pelicula> buscarPorTitulo(String titulo) {
        return peliculaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Pelicula> buscarPorGenero(String genero) {
        return peliculaRepository.findByGeneroIgnoreCase(genero);
    }

}
