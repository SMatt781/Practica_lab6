package com.example.practicalab6;

import com.example.practicalab6.Entity.Rol;
import com.example.practicalab6.Entity.Usuario;
import com.example.practicalab6.Repository.RolRepository;
import com.example.practicalab6.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Se ejecuta automáticamente al iniciar la app.
// Crea los roles y usuarios por defecto si no existen.
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Crear roles si no existen
        if (rolRepository.count() == 0) {
            Rol rolAdmin = new Rol();
            rolAdmin.setNombre("admin");
            rolRepository.save(rolAdmin);

            Rol rolUser = new Rol();
            rolUser.setNombre("user");
            rolRepository.save(rolUser);

            System.out.println("[DataLoader] Roles creados: admin, user");
        }

        // Crear usuarios si no existen
        if (usuarioRepository.count() == 0) {
            Rol rolAdmin = rolRepository.findByNombre("admin");
            Rol rolUser  = rolRepository.findByNombre("user");

            // Usuario administrador
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setEmail("admin@cine.com");
            admin.setPwd(passwordEncoder.encode("admin123"));  // BCrypt automático
            admin.setActivo(true);
            admin.setRol(rolAdmin);
            usuarioRepository.save(admin);

            // Usuario normal
            Usuario user = new Usuario();
            user.setNombre("Usuario");
            user.setEmail("user@cine.com");
            user.setPwd(passwordEncoder.encode("user123"));
            user.setActivo(true);
            user.setRol(rolUser);
            usuarioRepository.save(user);

            System.out.println("[DataLoader] Usuarios creados:");
            System.out.println("  admin@cine.com  /  admin123");
            System.out.println("  user@cine.com   /  user123");
        }
    }

}
