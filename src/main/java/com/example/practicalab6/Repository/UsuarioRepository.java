package com.example.practicalab6.Repository;

import com.example.practicalab6.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar por email (usado en el success handler de Security)
    Usuario findByEmail(String email);

}
