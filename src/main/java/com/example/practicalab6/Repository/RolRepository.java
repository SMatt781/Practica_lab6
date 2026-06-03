package com.example.practicalab6.Repository;

import com.example.practicalab6.Entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Rol findByNombre(String nombre);

}
