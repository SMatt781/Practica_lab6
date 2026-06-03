package com.example.practicalab6.Controller;

import com.example.practicalab6.Entity.Pelicula;
import com.example.practicalab6.Service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    // ------------------------------------------------
    // Listado principal (con búsqueda opcional)
    // ------------------------------------------------
    @GetMapping("/")
    public String lista(
            @RequestParam(required = false) String criterio,
            @RequestParam(required = false) String valor,
            Model model) {

        List<Pelicula> peliculas;

        if (valor != null && !valor.isBlank()) {
            if ("genero".equals(criterio)) {
                peliculas = peliculaService.buscarPorGenero(valor);
            } else {
                // Por defecto busca por título
                peliculas = peliculaService.buscarPorTitulo(valor);
            }
        } else {
            peliculas = peliculaService.listarTodas();
        }

        model.addAttribute("peliculas", peliculas);
        model.addAttribute("criterio", criterio);
        model.addAttribute("valor", valor);
        return "lista";
    }

    // ------------------------------------------------
    // Formulario NUEVA película
    // ------------------------------------------------
    @GetMapping("/nueva")
    public String formularioNueva(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        model.addAttribute("accion", "nueva");
        return "form";
    }

    @PostMapping("/nueva")
    public String guardarNueva(
            @Valid @ModelAttribute Pelicula pelicula,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("accion", "nueva");
            return "form";
        }

        peliculaService.guardar(pelicula);
        redirectAttributes.addFlashAttribute("mensaje", "Película registrada correctamente.");
        return "redirect:/";
    }

    // ------------------------------------------------
    // Formulario EDITAR película
    // ------------------------------------------------
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        Pelicula pelicula = peliculaService.buscarPorId(id);

        if (pelicula == null) {
            return "redirect:/";
        }

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("accion", "editar");
        return "form";
    }

    @PostMapping("/editar/{id}")
    public String guardarEdicion(
            @PathVariable Integer id,
            @Valid @ModelAttribute Pelicula pelicula,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("accion", "editar");
            return "form";
        }

        pelicula.setId(id);
        peliculaService.guardar(pelicula);
        redirectAttributes.addFlashAttribute("mensaje", "Película actualizada correctamente.");
        return "redirect:/";
    }

    // ------------------------------------------------
    // Eliminar película
    // ------------------------------------------------
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        peliculaService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Película eliminada correctamente.");
        return "redirect:/";
    }

}
