package tfm.alzi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Ejercicio;
import tfm.alzi.services.EjercicioService;

@Controller
public class EjercicioController {
    
    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping(value = "/show-ejercicio/{ejercicioId}")
    public String showPrograma(@PathVariable("ejercicioId") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = ejercicioService.getEjercicioById(ejercicioId);
            model.addAttribute("ejercicio", e);
            return "showEjercicio";
        }
        return "login";
    }

}
