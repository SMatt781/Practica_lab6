package com.example.practicalab6.Security;

import com.example.practicalab6.Entity.Usuario;
import com.example.practicalab6.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {

    // Para conectar Spring Security con la BD
    final DataSource dataSource;

    // Para obtener el objeto Usuario y guardarlo en sesión
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Para manejar redirecciones personalizadas
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // -------------------------------------------------------
    // Encriptación BCrypt (fuerza 10, como indica la clase)
    // -------------------------------------------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // -------------------------------------------------------
    // Configuración de rutas protegidas y formulario de login
    // (Clase 6.1 - Spring Security)
    // -------------------------------------------------------
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Rutas protegidas por rol
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/loginForm").permitAll()
                .requestMatchers("/nueva", "/editar/**", "/eliminar/**").hasAuthority("admin")
                .anyRequest().authenticated()
        );

        // Formulario de login personalizado
        http.formLogin(form -> form
                .loginPage("/loginForm")                  // Vista de login propia
                .loginProcessingUrl("/processLogin")      // URL que procesa el POST
                .successHandler((request, response, authentication) -> {

                    // ── Spring Session: guardar usuario en sesión ──
                    // (Clase 6.2 - Spring Session)
                    String email = authentication.getName();
                    Usuario usuario = usuarioRepository.findByEmail(email);
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioLogueado", usuario);

                    // ── Redirección: si vino de una URL protegida, volver ahí ──
                    DefaultSavedRequest savedRequest =
                            (DefaultSavedRequest) request.getSession()
                                    .getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    if (savedRequest != null) {
                        redirectStrategy.sendRedirect(request, response,
                                savedRequest.getRedirectUrl());
                    } else {
                        // Redirigir según rol (admin → /, user → /)
                        String rol = authentication.getAuthorities()
                                .iterator().next().getAuthority();
                        response.sendRedirect("/");
                    }
                })
        );

        // Cierre de sesión
        http.logout(logout -> logout
                .logoutSuccessUrl("/loginForm")   // Redirige al login
                .deleteCookies("JSESSIONID")      // Elimina la cookie de sesión
                .invalidateHttpSession(true)       // Borra todos los atributos de sesión
        );

        return http.build();
    }

    // -------------------------------------------------------
    // Leer usuarios y roles desde la base de datos
    // (Clase 6.1 - UserDetailsManager)
    // -------------------------------------------------------
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Query para obtener email, pwd y activo del usuario
        users.setUsersByUsernameQuery(
                "SELECT email, pwd, activo FROM usuario WHERE email = ?");

        // Query para obtener el rol del usuario (el orden de columnas importa)
        users.setAuthoritiesByUsernameQuery(
                "SELECT u.email, r.nombre " +
                "FROM usuario u " +
                "INNER JOIN rol r ON u.rol_id = r.id " +
                "WHERE u.email = ? AND u.activo = 1");

        return users;
    }

}
