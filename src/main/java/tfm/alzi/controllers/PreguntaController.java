package tfm.alzi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.InformePregunta;
import tfm.alzi.models.Pregunta;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.EjercicioPreguntaService;
import tfm.alzi.services.InformePreguntaService;
import tfm.alzi.services.PreguntaService;
import tfm.alzi.services.UsuarioService;

@Controller
public class PreguntaController {
    
    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EjercicioPreguntaService ejercicioPreguntaService;

    @Autowired
    private InformePreguntaService informePreguntaService;

    @GetMapping(value = "/preguntas") 
    public String listPreguntas(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            
            model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
            model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));

            return "preguntas";
        }
        return "login";
    }

    @GetMapping(value = "/pregunta") 
    public String showPregunta(@RequestParam(value = "id") final long preguntaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = this.preguntaService.getPreguntaById(preguntaId);
            model.addAttribute("pregunta", p);

            return "especialista/showPregunta";
        }
        return "login";
    }

    @GetMapping(value = "/ocultar-pregunta") 
    public String hidePregunta(@RequestParam(value = "id") final long preguntaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = this.preguntaService.getPreguntaById(preguntaId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(p.getUsuarioId() == u.getId()){
                p.setPublico(false);
                this.preguntaService.editarPregunta(p);
            }

            model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
            model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("preguntaOcultada", "Pregunta ocultada con éxito!");

            return "preguntas";
        }
        return "login";
    }

    @GetMapping(value = "/publicar-pregunta") 
    public String publishPregunta(@RequestParam(value = "id") final long preguntaId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = this.preguntaService.getPreguntaById(preguntaId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(p.getUsuarioId() == u.getId()){
                p.setPublico(true);
                this.preguntaService.editarPregunta(p);
            }

            model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
            model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("preguntaPublicada", "Pregunta publicada con éxito!");

            return "preguntas";
        }
        return "login";
    }

    @RequestMapping(value = "/borrar-pregunta")
    public String eliminarPregunta(@RequestParam(value = "id") final long preguntaId,
    Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
			
            /*
            this.informeProgramaService.eliminarLista(this.informeProgramaService.findByProgramaId(programaId));
            this.informeEjercicioService.eliminarLista(this.informeEjercicioService.findByProgramaId(programaId));
            this.informePreguntaService.eliminarLista(this.informePreguntaService.findByProgramaId(programaId));
            this.participanteProgramaService.eliminarLista(this.participanteProgramaService.findByProgramaId(programaId));
            this.programaEjercicioService.eliminarLista(this.programaEjercicioService.getEjerciciosByProgramaID(programaId));
            this.programaService.eliminarPrograma(programaId);
            */

            //ejercicioPregunta
            this.ejercicioPreguntaService.eliminarLista(this.ejercicioPreguntaService.getEjerciciosPreguntaByPreguntaId(preguntaId));
            //informePregunta
            this.informePreguntaService.eliminarLista(this.informePreguntaService.getInformePreguntaByPreguntaId(preguntaId));
            //pregunta
            this.preguntaService.eliminarPregunta(preguntaId);


            model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
            model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
			model.addAttribute("preguntaEliminada", "Pregunta eliminado con éxito.");
			
            return "preguntas";
		} else {
			return "login";
		}
	}

}
