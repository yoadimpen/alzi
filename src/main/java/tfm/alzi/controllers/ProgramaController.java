package tfm.alzi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Ejercicio;
import tfm.alzi.models.EjercicioPregunta;
import tfm.alzi.models.InformeEjercicio;
import tfm.alzi.models.InformePregunta;
import tfm.alzi.models.InformePrograma;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.models.ProgramaEjercicio;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.EjercicioPreguntaService;
import tfm.alzi.services.EjercicioService;
import tfm.alzi.services.InformeEjercicioService;
import tfm.alzi.services.InformePreguntaService;
import tfm.alzi.services.InformeProgramaService;
import tfm.alzi.services.ParticipanteProgramaService;
import tfm.alzi.services.ProgramaEjercicioService;
import tfm.alzi.services.ProgramaService;
import tfm.alzi.services.UsuarioService;

@Controller
public class ProgramaController {
    
    @Autowired
    private ProgramaService programaService;

    @Autowired
    private ProgramaEjercicioService programaEjercicioService;

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private InformeEjercicioService informeEjercicioService;

    @Autowired
    private InformePreguntaService informePreguntaService;

    @Autowired
    private InformeProgramaService informeProgramaService;

    @Autowired
    private ParticipanteProgramaService participanteProgramaService;

    @Autowired
    private EjercicioPreguntaService ejercicioPreguntaService;

    @GetMapping(value = "/show-programa") 
    public String showPrograma(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = programaService.getProgramaById(programaId);
            model.addAttribute("programa", p);
            List<ProgramaEjercicio> programaEjercicio = this.programaEjercicioService.getEjerciciosByProgramaID(programaId);
            List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
            for (ProgramaEjercicio pe: programaEjercicio){
                ejercicios.add(this.ejercicioService.getEjercicioById(pe.getEjercicioId()));
            }
            model.addAttribute("ejercicios", ejercicios);

            Principal principal = request.getUserPrincipal();
            String dni = principal.getName();
            Usuario usuario = this.usuarioService.getUsuarioByDNI(dni);

            model.addAttribute("uId",usuario.getId());

            return "usuario/showPrograma";
        }
        return "login";
    }

    @GetMapping(value = "/programa") 
    public String showProgramaEspecialista(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = this.programaService.getProgramaById(programaId);
            System.out.println(p.getId());
            model.addAttribute("programa", p);

            List<ProgramaEjercicio> programaEjercicio = this.programaEjercicioService.getEjerciciosByProgramaID(programaId);
            List<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
            for (ProgramaEjercicio pe: programaEjercicio){
                ejercicios.add(this.ejercicioService.getEjercicioById(pe.getEjercicioId()));
            }
            model.addAttribute("ejercicios", ejercicios);

            return "especialista/showPrograma";
        }
        return "login";
    }

    @GetMapping(value = "/ocultar-programa") 
    public String hidePrograma(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = programaService.getProgramaById(programaId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(p.getUsuarioId() == u.getId()){
                p.setPublico(false);
                this.programaService.editarPrograma(p);
            }

            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("programaOcultado", "Programa ocultado con éxito!");

            return "programas";
        }
        return "login";
    }

    @GetMapping(value = "/publicar-programa") 
    public String publishPrograma(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = programaService.getProgramaById(programaId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(p.getUsuarioId() == u.getId()){
                p.setPublico(true);
                this.programaService.editarPrograma(p);
            }

            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("programaPublicado", "Programa publicado con éxito!");

            return "programas";
        }
        return "login";
    }
    
    @GetMapping(value = "/crear-programa") 
    public String crearProgramaForm(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = new Programa();

            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());
            p.setUsuarioId(u.getId());
            p.setPublico(false);
            p.setPuntuacion(0);

            model.addAttribute("programa", p);

            List<Ejercicio> ejercicios = this.ejercicioService.getAllPublicEjercicios();
            model.addAttribute("ejercicios", ejercicios);

            return "especialista/formNewPrograma";
        }
        return "login";
    }

    @PostMapping(value = "/crear-programa")
    public String crearPrograma(@ModelAttribute("programa") @Valid final Programa programa,
    final BindingResult result, @RequestParam("tipoDuracion") String tipoDuracion,
    @ModelAttribute("ejerciciosArray") String ejercicios,
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            this.validarPrograma(result, programa);

            if(result.hasErrors()) {
                model.addAttribute("programa", programa);
                List<Ejercicio> ejs = this.ejercicioService.getAllPublicEjercicios();
                model.addAttribute("ejercicios", ejs);

                return "especialista/formNewPrograma";
            } else {
                String[] arrayEjercicio = ejercicios.split(",");
                if(arrayEjercicio.length >= 2){
                    programa.setPublico(true);
                }
                this.programaService.crearPrograma(programa);
                for (String e:arrayEjercicio){
                    ProgramaEjercicio rel = new ProgramaEjercicio();
                    rel.setProgramaId(programa.getId());
                    rel.setEjercicioId(Long.valueOf(e));
                    this.programaEjercicioService.crearRelacion(rel);
                }
            }

            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("programaCreado", "Programa creado con éxito!");

            return "programas";
        
        }
        return "login";
		
	}

    public void validarPrograma(final BindingResult result, final Programa programa){

        if(programa.getTitulo().isEmpty() || programa.getTitulo().isBlank()) {
			result.rejectValue("titulo","titulo","Introduzca un título.");
		}

        if(programa.getDescripcion().isEmpty() || programa.getDescripcion().isBlank()) {
			result.rejectValue("descripcion", "descripcion","Introduzca una descripción.");
		}

        if(programa.getDuracion() == null){
            result.rejectValue("duracion", "duracion", "Introduzca una duración.");
        }

        if(programa.getDuracion() != null){
            if(programa.getDuracion() <= 0)
            result.rejectValue("duracion", "duracion", "La duración debe ser mayor que 0 días/meses.");
        }

    }

    @GetMapping(value = "/editar-programa") 
    public String editarProgramaForm(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Programa p = this.programaService.getProgramaById(programaId);
            System.out.println(p.getId());
            model.addAttribute("programa", p);

            return "especialista/formEditPrograma";
        }
        return "login";
    }

    @PostMapping(value = "/editar-programa")
    public String editarPrograma(@RequestParam("tipoDuracion") String tipoDuracion,
    @ModelAttribute("id") String programaId,
    @ModelAttribute("programa") @Valid final Programa programa, final BindingResult result, 
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            programa.setId(Long.valueOf(programaId));

            this.validarPrograma(result, programa);

            if(result.hasErrors()) {
                model.addAttribute("programa", programa);
                return "especialista/formEditPrograma";
            } else {
                this.programaService.editarPrograma(programa);
            }

            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("programaEditado", "Programa editado con éxito.");

            return "programas";
        
        }
        return "login";
		
	}

    @RequestMapping(value = "/borrar-programa")
    public String eliminarPrograma(@RequestParam(value = "id") final long programaId,
    Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
			
            this.informeProgramaService.eliminarLista(this.informeProgramaService.findByProgramaId(programaId));
            this.informeEjercicioService.eliminarLista(this.informeEjercicioService.findByProgramaId(programaId));
            this.informePreguntaService.eliminarLista(this.informePreguntaService.findByProgramaId(programaId));
            this.participanteProgramaService.eliminarLista(this.participanteProgramaService.findByProgramaId(programaId));
            this.programaEjercicioService.eliminarLista(this.programaEjercicioService.getEjerciciosByProgramaID(programaId));
            this.programaService.eliminarPrograma(programaId);

            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
			model.addAttribute("programaEliminado", "Programa eliminado éxito.");
			return "programas";
		} else {
			return "login";
		}
	}

    @GetMapping(value = "/actualizar-ejercicios") 
    public String actualizarEjerciciosForm(@RequestParam(value = "programaId") final long programaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            List<ProgramaEjercicio> ls = this.programaEjercicioService.getEjerciciosByProgramaID(programaId);
            List<Ejercicio> ejercicios = this.ejercicioService.getAllPublicEjercicios();

            List<Ejercicio> ejerciciosCurrent = new ArrayList<Ejercicio>();
            for (ProgramaEjercicio pe: ls){
                ejerciciosCurrent.add(this.ejercicioService.getEjercicioById(pe.getEjercicioId()));
            }

            model.addAttribute("ejerciciosCurrent", ejerciciosCurrent);
            model.addAttribute("ejercicios", ejercicios);
            model.addAttribute("programaId", programaId);

            return "especialista/actualizarEjercicios";
        }
        return "login";
    }

    @PostMapping(value = "/actualizar-ejercicios")
    public String actualizarEjercicios(@ModelAttribute("programaId") long programaId,
    @ModelAttribute("ejerciciosArray") String ejercicios,
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            List<ProgramaEjercicio> ejerciciosCurrent = this.programaEjercicioService.getEjerciciosByProgramaID(programaId);
            List<Long> ids = new ArrayList<>();
            
            for(ProgramaEjercicio pe: ejerciciosCurrent){
                ids.add(pe.getEjercicioId());
            }

            String[] arrayEjercicio = ejercicios.split(",");
            List<Long> idsNew = new ArrayList<>();
            for(String s: arrayEjercicio){
                idsNew.add(Long.valueOf(s.trim()));
            }

            List<Long> toRemove = new ArrayList<>();
            List<Long> toCreate = new ArrayList<>();

            for(long id: ids){
                if(!idsNew.contains(id)){
                    toRemove.add(id);
                }
            }

            for(long id2: idsNew){
                if(!ids.contains(id2)){
                    toCreate.add(id2);
                }
            }

            for (Long r:toRemove){
                ProgramaEjercicio rel = this.programaEjercicioService.findByProgramaIdEjercicioId(programaId, r);
                this.programaEjercicioService.eliminarRelacion(rel);
            }
            
            for (Long c:toCreate){
                ProgramaEjercicio rel = new ProgramaEjercicio();
                rel.setProgramaId(programaId);
                rel.setEjercicioId(c);
                this.programaEjercicioService.crearRelacion(rel);
            }

            System.out.println(toRemove);
            System.out.println(toCreate);

            return "redirect:/programa?id=" + programaId;
        
        }
        return "login";
		
	}

    @GetMapping(value = "/asignar")
    public String asignarPrograma(@ModelAttribute("usuarioId") final Long usuarioId,
    final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			List<Programa> programas = this.programaService.getAllPublicProgramas();
			model.addAttribute("usuarioId", usuarioId);
			model.addAttribute("programas", programas);
			
			return "especialista/assign";
		} 
	}

	@PostMapping(value = "/asignar")
    public String asignarPrograma(@ModelAttribute("usuarioId") Long usuarioId,
	@ModelAttribute("programas") long programas,
	final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "login";
		} else {
			
            ParticipantePrograma pp = new ParticipantePrograma();
            pp.setProgramaId(programas);
            pp.setUsuarioId(usuarioId);
            this.participanteProgramaService.crearRel(pp);

            InformePrograma ipro = new InformePrograma();
            ipro.setUsuarioId(usuarioId);
            ipro.setProgramaId(programas);
            ipro.setProgreso(0);
            ipro.setAciertos(0);
            ipro.setFallos(0);
            ipro.setObservaciones(null);
            this.informeProgramaService.crearInforme(ipro);
            List<ProgramaEjercicio> ejercicios = this.programaEjercicioService.getEjerciciosByProgramaID(programas);
            for(ProgramaEjercicio pe: ejercicios){
                InformeEjercicio ie = new InformeEjercicio();
                ie.setUsuarioId(usuarioId);
                ie.setProgramaId(programas);
                ie.setEjercicioId(pe.getEjercicioId());
                ie.setAciertos(0);
                ie.setFallos(0);
                ie.setObservaciones(null);
                ie.esFinalizado(false);
                this.informeEjercicioService.crearInforme(ie);

                List<EjercicioPregunta> preguntas = this.ejercicioPreguntaService.findByEjercicioId(pe.getEjercicioId());
                for (EjercicioPregunta ep: preguntas){
                    InformePregunta ipre = new InformePregunta();
                    ipre.setProgramaId(programas);
                    ipre.setEjercicioId(pe.getEjercicioId());
                    ipre.setPreguntaId(ep.getPreguntaId());
                    ipre.setUsuarioId(usuarioId);
                    ipre.setRespuesta(null);
                    ipre.setResultado(false);
                    this.informePreguntaService.crearInforme(ipre);
                }
            }
			
			return "redirect:/show-perfil?usuarioId=" + usuarioId;
		} 
	}

}