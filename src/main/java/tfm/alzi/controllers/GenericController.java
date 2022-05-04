package tfm.alzi.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.UsuarioService;

@Controller
public class GenericController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/alzi")
    public String getAlzi(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
			return "index";
		} else {
            return "login";
        } 
	}
    
    @RequestMapping("/area-personal")
    public String goToAreaPersonal(final Model model, final HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
			return "areaPersonal";
		} else {
            return "login";
        } 
    }

    @RequestMapping("/entrenamiento")
    public String goToEntrenamiento(final Model model, final HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
			return "entrenamiento";
		} else {
            return "login";
        } 
    }

    @RequestMapping("/perfil")
    public String goToPerfil(final Model model, final HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            Principal principal = request.getUserPrincipal();
            String dni = principal.getName();
            Usuario usuario = this.usuarioService.getUsuarioByDNI(dni);
            model.addAttribute("usuario", usuario);
			return "perfil";
		} else {
            return "login";
        }
    }

    @RequestMapping("/ajustes")
    public String goToAjustes(final Model model, final HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
			return "ajustes";
		} else {
            return "login";
        } 
    }

}
