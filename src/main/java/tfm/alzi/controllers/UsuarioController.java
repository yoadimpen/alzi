package tfm.alzi.controllers;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.Valid;

import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/login")
	public String login(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
			return "index";
		}
		return "login";
	}

    @GetMapping(value = "/crear-usuario")
    public String crearUsuarioForm(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
			return "home";
		}
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
        model.addAttribute("fechaNacimiento", LocalDate.now());

		return "formNewUsuario";
	}

    @PostMapping(value = "/crear-usuario")
    public String crearusuario(@Valid @ModelAttribute("usuario") final Usuario usuario, final BindingResult result, final Model model) {

		//this.validarUsuario(usuario, result);

		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "usuarios/formNewUsuario";
		} else {
			this.usuarioService.creaUsuario(usuario);
		}
		return "redirect:/";
	}

    /*

    public void validarUsuario(final Usuario usuario, final BindingResult result, String confirmPassword) {

		// validar que el username es único
		if (this.usuarioService.numeroUsuariosByUsername(usuario.getUsername()) != 0) {
			result.rejectValue("username", "username", "El usuario introducido ya existe");
		}
		if (this.usuarioService.numeroUsuariosByEmail(usuario.getEmail()) != 0) {
			result.rejectValue("email", "email", "El email introducido ya existe");
		}
		//validar que las contraseñas coinciden
        if(!confirmPassword.equals(usuario.getPassword())){
            result.rejectValue("password","password","Las contraseñas no coinciden");
        }
	}

    */

    public Usuario getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        Usuario us = null;
        if (principal instanceof Usuario) {
            us = (Usuario) principal;
        }
        return us;
    }
    
}
