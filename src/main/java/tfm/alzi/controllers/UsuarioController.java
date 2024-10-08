package tfm.alzi.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.models.Usuario;
import tfm.alzi.models.UsuarioCuidador;
import tfm.alzi.models.UsuarioEspecialista;
import tfm.alzi.services.ParticipanteProgramaService;
import tfm.alzi.services.ProgramaService;
import tfm.alzi.services.UsuarioCuidadorService;
import tfm.alzi.services.UsuarioEspecialistaService;
import tfm.alzi.services.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

	@Autowired
	private ParticipanteProgramaService participanteProgramaService;

	@Autowired
	private ProgramaService programaService;

	@Autowired
	private UsuarioCuidadorService usuarioCuidadorService;

	@Autowired
	private UsuarioEspecialistaService usuarioEspecialistaService;

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
			return "index";
		}
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);

		return "formNewUsuario";
	}

    @PostMapping(value = "/crear-usuario")
    public String crearUsuario(@ModelAttribute("usuario") @Valid final Usuario usuario, final BindingResult result, final Model model) {

		this.validarUsuario(usuario, result, true, null);

		//System.out.println(result.getAllErrors());

		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "formNewUsuario";
		} else {
			this.usuarioService.creaUsuario(usuario);
			model.addAttribute("usuarioCreado", "El usuario se ha creado con éxito!");
		}
		return "login"; 
	}

    public void validarUsuario(final Usuario usuario, final BindingResult result, Boolean nuevo, String passCheck) {

		System.out.println(usuario.getRoles());

		String dniPattern = "^([A-Z]?[0-9]{7}[A-Z]{1})$";
		String passPattern = "^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";
		String telefonoPattern = "^([0-9]{9})$";
		String emailPattern = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
		String rolesPattern = "^(USUARIO|CUIDADOR|ESPECIALISTA)$";
		String cuidadorPattern = "^(INFORMAL|FORMAL)$";

		if(usuario.getNombre() == null | usuario.getNombre().isEmpty() | usuario.getNombre().isBlank()) {
			result.rejectValue("nombre", "nombre","Debes introducir tu nombre.");
		}
		if(usuario.getApellidos() == null | usuario.getApellidos().isEmpty() | usuario.getNombre().isBlank()) {
			result.rejectValue("apellidos", "apellidos","Debes introducir tus apellidos.");
		}
		if(usuario.getFechaNacimiento() == null){
			result.rejectValue("fechaNacimiento", "fechaNacimiento","Debes introducir tu fecha de nacimiento.");
		}
		if(usuario.getFechaNacimiento() != null && usuario.getFechaNacimiento().isAfter(LocalDate.now())) {
			result.rejectValue("fechaNacimiento", "fechaNacimiento", "La fecha de nacimiento introducida debe ser anterior a la actual.");
		}
		if(usuario.getDni() == null | usuario.getDni().isEmpty() | usuario.getDni().isBlank()) {
			result.rejectValue("dni", "dni", "Debes introducir tu DNI/NIE.");
		}
		if(!usuario.getDni().matches(dniPattern)) {
			result.rejectValue("dni", "dni", "El DNI/NIE introducido debe ser válido.");
		}
		if(nuevo == true) {
			if (this.usuarioService.getNumUsuariosByDNI(usuario.getDni()) != 0) {
				result.rejectValue("dni", "dni", "El usuario introducido ya existe.");
			}
		}
		if (usuario.getPass() == null | usuario.getPass().isEmpty() | usuario.getPass().isBlank()){
			result.rejectValue("pass", "pass", "Debes introducir una contraseña.");
		}
		if (usuario.getPass() != null & !usuario.getPass().matches(passPattern)) {
			result.rejectValue("pass", "pass", "La contraseña debe tener mínimo 6 caracteres, un dígito, una minúscula y una mayúscula.");
		}
		if (nuevo == false){
			if (!usuario.getPass().equals(passCheck)) {
				result.rejectValue("pass", "pass", "Las contraseñas no coinciden.");
			}
		}
		if (usuario.getDireccion() == null | usuario.getDireccion().isEmpty() | usuario.getDireccion().isBlank()){
			result.rejectValue("direccion", "direccion", "Debes introducir una dirección.");
		}
		if (usuario.getTelefono() == null){
			result.rejectValue("telefono", "telefono", "Debes introducir un número de teléfono.");
		}
		if (usuario.getTelefono() != null && !usuario.getTelefono().toString().matches(telefonoPattern)) {
			result.rejectValue("telefono", "telefono", "El número de teléfono introducido debe ser válido.");
		}
		if (usuario.getEmail() == null | usuario.getEmail().isEmpty() | usuario.getEmail().isBlank()) {
			result.rejectValue("email", "email", "Debes introducir un correo electrónico.");
		}
		if (!usuario.getEmail().matches(emailPattern)) {
			result.rejectValue("email", "email", "El correo electrónico introducido debe ser válido.");
		}
		if(nuevo == true){
			if (this.usuarioService.getNumUsuariosByEmail(usuario.getEmail()) != 0) {
				result.rejectValue("email", "email", "El email introducido ya existe.");
			}
		}
		if (!usuario.getEmail().contains("@gmail.com")) {
			result.rejectValue("email", "email", "El email introducido debe ser de Gmail.");
		}
		if (usuario.getRoles() == null | usuario.getRoles().isEmpty() | usuario.getRoles().isBlank()) {
			result.rejectValue("roles", "roles", "Deber elegir un tipo de perfil.");
		}
		if (!usuario.getRoles().matches(rolesPattern)) {
			result.rejectValue("roles", "roles", "Los perfiles disponibles son USUARIO, CUIDADOR y ESPECIALISTA.");
		}
		if (usuario.getRoles().contains("USUARIO")) {
			if (usuario.getPRelacionCuidador() == null | usuario.getPRelacionCuidador().isEmpty() | usuario.getPRelacionCuidador().isBlank()) {
				result.rejectValue("PRelacionCuidador", "PRelacionCuidador", "Debes introducir la relación con el cuidador.");
			}
		}
		if (usuario.getRoles().contains("CUIDADOR")) {
			if ((usuario.getCTipo()) == null | usuario.getCTipo().isEmpty() | usuario.getCTipo().isBlank() | !usuario.getCTipo().matches(cuidadorPattern)) {
				result.rejectValue("CTipo", "CTipo", "Debes introducir el tipo de cuidador (INFORMAL o FORMAL).");
			}
		}
		if (usuario.getRoles().contains("ESPECIALISTA")) {
			if (usuario.getEEspecialidad() == null | usuario.getEEspecialidad().isEmpty() | usuario.getEEspecialidad().isBlank()) {
				result.rejectValue("EEspecialidad", "EEspecialidad", "Debes introducir la especialidad que tienes.");
			}
		}
		if (usuario.getRoles().contains("ESPECIALISTA")) {
			if (usuario.getECentro() == null | usuario.getECentro().isEmpty() | usuario.getECentro().isBlank()) {
				result.rejectValue("ECentro", "ECentro", "Debes introducir el centro en el que trabajas.");
			}
		}
	}

	@GetMapping(value = "/editar-usuario")
    public String editarUsuarioForm(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "index";
		} else {
			Usuario usuario = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());
			usuario.setPass("");
			model.addAttribute("usuario", usuario);
			//System.out.println(usuario.getRoles());
			return "formEditUsuario";
		} 
	}

	@PostMapping(value = "/editar-usuario")
    public String editarUsuario(@ModelAttribute("usuario") @Valid final Usuario usuario, final BindingResult result, @RequestParam(value = "passCheck") String passCheck, @RequestParam(value = "pass") String pass, final Model model) {

		this.validarUsuario(usuario, result, false, passCheck);

		//System.out.println(usuario.getCTipo());
		//System.out.println(result.getAllErrors());

		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "formEditUsuario"; 
		} else {
			//System.out.println(pass);
			usuario.setPass(pass);
			this.usuarioService.editarUsuario(usuario);
			model.addAttribute("successEdit", "Cuenta actualizada con éxito!");
			return "perfil";
		}
	}

	@RequestMapping(value = "/eliminar-usuario")
    public String eliminarUsuario(final HttpServletRequest request, final Model model) {
		if (request.getUserPrincipal() != null) {
			this.usuarioService.deleteUsuario(request.getUserPrincipal().getName());
			try {
				request.logout();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addAttribute("usuarioEliminado", "Cuenta eliminada con éxito.");
			return "login";
		} else {
			return "index";
		}
	}

	@GetMapping(value = "/show-perfil")
    public String showPerfilProgramas(final Model model,
	@RequestParam(value = "usuarioId") long usuarioId,final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "index";
		} else {
			model.addAttribute("usuario", this.usuarioService.getUsuarioById(usuarioId));

			List<ParticipantePrograma> ls = this.participanteProgramaService.getSuscripcionesByID(usuarioId);
			List<Programa> programas = new ArrayList<>();

			for(ParticipantePrograma p:ls){
				programas.add(this.programaService.getProgramaById(p.getProgramaId()));
			}

			model.addAttribute("programas", programas);
			model.addAttribute("usuarioId", usuarioId);

			return "showPerfil";
		} 
	}

	@GetMapping(value = "/carer")
    public String addCuidador(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			List<Usuario> cuidadores = this.usuarioService.getAllCuidadores();
			model.addAttribute("usuario", this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()));
			model.addAttribute("cuidadores", cuidadores);
			
			return "usuario/carer";
		} 
	}

	@PostMapping(value = "/carer")
    public String addCuidadorPost(@ModelAttribute("usuarioId") long usuarioId,
	@ModelAttribute("cuidadores") long cuidadores,
	final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			UsuarioCuidador possible = this.usuarioCuidadorService.findByUsuarioId(usuarioId);
			if(possible == null){
				UsuarioCuidador uc = new UsuarioCuidador();
				uc.setUsuarioId(usuarioId);
				uc.setCuidadorId(cuidadores);
				this.usuarioCuidadorService.crearRel(uc);
			}
			
			return "redirect:/perfil";
		} 
	}

	@GetMapping(value = "/usuario")
    public String addUsuario(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			List<Usuario> usuarios = this.usuarioService.getAllUsuarios();
			model.addAttribute("especialista", this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()));
			model.addAttribute("usuarios", usuarios);
			
			return "especialista/usuario";
		} 
	}

	@PostMapping(value = "/usuario")
    public String addUsuarioPost(@ModelAttribute("especialistaId") long especialistaId,
	@ModelAttribute("usuarios") long usuarios,
	final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			UsuarioEspecialista possible = this.usuarioEspecialistaService.findByBoth(especialistaId,usuarios);
			if(possible == null){
				UsuarioEspecialista ue = new UsuarioEspecialista();
				ue.setEspecialidadId(especialistaId);
				ue.setUsuarioId(usuarios);
				this.usuarioEspecialistaService.crearRel(ue);
			} else {
				List<Usuario> users = this.usuarioService.getAllUsuarios();
				model.addAttribute("especialista", this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()));
				model.addAttribute("usuarios", users);
				model.addAttribute("existe", true);
				return "especialista/usuario";
			}
			
			return "redirect:/alzi";
		} 
	}

    public Usuario getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        Usuario us = null;
        if (principal instanceof Usuario) {
            us = (Usuario) principal;
        }
        return us;
    }

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	Map<String, String> errors = new HashMap<>();
	
	for (FieldError error:ex.getBindingResult().getFieldErrors()){
		errors.put(error.getField(), error.getDefaultMessage());
	}
	
	System.out.println("-----------------------------------------------------------------" + errors);
	return errors;
	}
    
}
