package com.example.practicalab6.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Muestra la página de login personalizada
    @GetMapping("/loginForm")
    public String loginForm() {
        return "auth/login";
    }

}
