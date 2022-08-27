package tfm.alzi.controllers;

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

    @GetMapping(value = "/crear-pregunta") 
    public String crearPregunta(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            return "especialista/formNewPreguntaPrev";
        }
        return "login";
    }

    @PostMapping(value = "/crear-pregunta") 
    public String crearPregunta(@ModelAttribute("tipo") final String tipo,
    final BindingResult result, final Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){

            if(tipo.equals("COMPLETA")){
                return "redirect:/crear-pregunta-completa";
            } else if(tipo.equals("UNIQUE")){
                return "redirect:/crear-pregunta-unique";
            } else {
                return "redirect:/crear-pregunta-multi";
            }
        
        }
        return "login";
    }

    @GetMapping(value = "/crear-pregunta-completa") 
    public String crearPreguntaCompleta(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = new Pregunta();
            p.setPublico(false);
            p.setUsuarioId(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId());
            p.setTipo("COMPLETA");
            model.addAttribute("pregunta", p);
            model.addAttribute("esCompleta", true);
            model.addAttribute("ruta", "completa");

            return "especialista/formNewPregunta";
        }
        return "login";
    }

    @PostMapping(value = "/crear-pregunta-completa") 
    public String crearPreguntaCompleta(@ModelAttribute("answer") @Valid final String answer,
    @ModelAttribute("pregunta") @Valid final Pregunta pregunta,
    final BindingResult result, final Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){

            pregunta.setAsnwer(answer);
            
            this.validarPregunta(result, pregunta);

            if(result.hasErrors()){
                model.addAttribute("pregunta", pregunta);
                model.addAttribute("esCompleta", true);
                model.addAttribute("ruta", "completa");

                return "especialista/formNewPregunta";
            } else {
                System.out.println("NO HAY ERRORES");

                pregunta.setOption1(null);
                pregunta.setOption2(null);
                pregunta.setOption3(null);
                pregunta.setOption4(null);
                pregunta.setLinkOption1(null);
                pregunta.setLinkOption2(null);
                pregunta.setLinkOption3(null);
                pregunta.setLinkOption4(null);
                pregunta.setCorrect1(null);
                pregunta.setCorrect2(null);
                pregunta.setCorrect3(null);
                pregunta.setCorrect4(null);

                if(pregunta.getLinkQuestion().isEmpty() || pregunta.getLinkQuestion().isBlank()){
                    pregunta.setLinkQuestion(null);
                }

                this.preguntaService.crearPregunta(pregunta);

                model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
                model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
                model.addAttribute("preguntaCreada", "Pregunta creada con éxito!");

                return "preguntas";

            }
        
        }
        return "login";
    }

    @GetMapping(value = "/crear-pregunta-unique") 
    public String crearPreguntaUnique(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = new Pregunta();
            p.setPublico(false);
            p.setUsuarioId(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId());
            p.setTipo("UNIQUE");
            model.addAttribute("pregunta", p);
            model.addAttribute("esUnique", true);
            model.addAttribute("ruta", "unique");

            return "especialista/formNewPregunta";
        }
        return "login";
    }

    @PostMapping(value = "/crear-pregunta-unique") 
    public String crearPreguntaUnique(@ModelAttribute("pregunta") @Valid final Pregunta pregunta,
    final BindingResult result, final Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){

            this.validarPregunta(result, pregunta);

            if(result.hasErrors()){
                model.addAttribute("pregunta", pregunta);
                model.addAttribute("esUnique", true);
                model.addAttribute("ruta", "unique");

                return "especialista/formNewPregunta";
            } else {
                System.out.println("NO HAY ERRORES");

                if(pregunta.isCorrect1()){
                    pregunta.setAsnwer(pregunta.getOption1());
                } else if(pregunta.isCorrect2()){
                    pregunta.setAsnwer(pregunta.getOption2());
                } else if(pregunta.isCorrect3()){
                    pregunta.setAsnwer(pregunta.getOption3());
                } else {
                    pregunta.setAsnwer(pregunta.getOption4());
                }

                if(pregunta.getOption1().isEmpty() || pregunta.getOption1().isBlank()){
                    pregunta.setOption1(null);
                    pregunta.setCorrect1(null);
                }
                if(pregunta.getOption2().isEmpty() || pregunta.getOption2().isBlank()){
                    pregunta.setOption2(null);
                    pregunta.setCorrect2(null);
                }
                if(pregunta.getOption3().isEmpty() || pregunta.getOption3().isBlank()){
                    pregunta.setOption3(null);
                    pregunta.setCorrect3(null);
                }
                if(pregunta.getOption4().isEmpty() || pregunta.getOption4().isBlank()){
                    pregunta.setOption4(null);
                    pregunta.setCorrect4(null);
                }
                if(pregunta.getLinkOption1().isEmpty() || pregunta.getLinkOption1().isBlank()){
                    pregunta.setLinkOption1(null);
                }
                if(pregunta.getLinkOption2().isEmpty() || pregunta.getLinkOption2().isBlank()){
                    pregunta.setLinkOption2(null);
                }
                if(pregunta.getLinkOption3().isEmpty() || pregunta.getLinkOption3().isBlank()){
                    pregunta.setLinkOption3(null);
                }
                if(pregunta.getLinkOption4().isEmpty() || pregunta.getLinkOption4().isBlank()){
                    pregunta.setLinkOption4(null);
                }
                if(pregunta.getLinkQuestion().isEmpty() || pregunta.getLinkQuestion().isBlank()){
                    pregunta.setLinkQuestion(null);
                }

                this.preguntaService.crearPregunta(pregunta);

                model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
                model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
                model.addAttribute("preguntaCreada", "Pregunta creada con éxito!");

                return "preguntas";
            }
        
        }
        return "login";
    }

    @GetMapping(value = "/crear-pregunta-multi") 
    public String crearPreguntaMulti(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Pregunta p = new Pregunta();
            p.setPublico(false);
            p.setUsuarioId(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId());
            p.setTipo("MULTI");
            model.addAttribute("pregunta", p);
            model.addAttribute("esMulti", true);
            model.addAttribute("ruta", "multi");

            return "especialista/formNewPregunta";
        }
        return "login";
    }

    @PostMapping(value = "/crear-pregunta-multi") 
    public String crearPreguntaMulti(@ModelAttribute("pregunta") @Valid final Pregunta pregunta,
    final BindingResult result, final Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){

            this.validarPregunta(result, pregunta);

            if(result.hasErrors()){
                model.addAttribute("pregunta", pregunta);
                model.addAttribute("esMulti", true);
                model.addAttribute("ruta", "multi");

                return "especialista/formNewPregunta";
            } else {
                System.out.println("NO HAY ERRORES");

                List<String> respuestas = new ArrayList<>();

                if(pregunta.isCorrect1()){
                    respuestas.add(pregunta.getOption1());
                }
                if(pregunta.isCorrect2()){
                    respuestas.add(pregunta.getOption2());
                }
                if(pregunta.isCorrect3()){
                    respuestas.add(pregunta.getOption3());
                }
                if(pregunta.isCorrect4()){
                    respuestas.add(pregunta.getOption4());
                }

                String res = "";

                for (String s: respuestas){
                    if(res == ""){
                        res = s;
                    } else {
                        res = res + "," + s;
                    }
                }

                if(res != ""){
                    pregunta.setAsnwer(res);
                }

                if(pregunta.getOption1().isEmpty() || pregunta.getOption1().isBlank()){
                    pregunta.setOption1(null);
                    pregunta.setCorrect1(null);
                }
                if(pregunta.getOption2().isEmpty() || pregunta.getOption2().isBlank()){
                    pregunta.setOption2(null);
                    pregunta.setCorrect2(null);
                }
                if(pregunta.getOption3().isEmpty() || pregunta.getOption3().isBlank()){
                    pregunta.setOption3(null);
                    pregunta.setCorrect3(null);
                }
                if(pregunta.getOption4().isEmpty() || pregunta.getOption4().isBlank()){
                    pregunta.setOption4(null);
                    pregunta.setCorrect4(null);
                }
                if(pregunta.getLinkOption1().isEmpty() || pregunta.getLinkOption1().isBlank()){
                    pregunta.setLinkOption1(null);
                }
                if(pregunta.getLinkOption2().isEmpty() || pregunta.getLinkOption2().isBlank()){
                    pregunta.setLinkOption2(null);
                }
                if(pregunta.getLinkOption3().isEmpty() || pregunta.getLinkOption3().isBlank()){
                    pregunta.setLinkOption3(null);
                }
                if(pregunta.getLinkOption4().isEmpty() || pregunta.getLinkOption4().isBlank()){
                    pregunta.setLinkOption4(null);
                }
                if(pregunta.getLinkQuestion().isEmpty() || pregunta.getLinkQuestion().isBlank()){
                    pregunta.setLinkQuestion(null);
                }

                this.preguntaService.crearPregunta(pregunta);

                model.addAttribute("preguntas", this.preguntaService.getAllPublicPreguntas());
                model.addAttribute("preguntasPriv", this.preguntaService.getMyPrivatePreguntas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
                model.addAttribute("preguntaCreada", "Pregunta creada con éxito!");

                return "preguntas";
            }
        
        }
        return "login";
    }

    private void validarPregunta(BindingResult result, @Valid Pregunta pregunta) {

        String urlPattern = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

        if(pregunta.getDescripcion().isEmpty() || pregunta.getDescripcion().isBlank()) {
			result.rejectValue("descripcion","descripcion","Introduzca una descripción.");
		}

        if(pregunta.getQuestion().isEmpty() || pregunta.getQuestion().isBlank()) {
			result.rejectValue("question","question","Introduzca una pregunta.");
		}

        if(!pregunta.getLinkQuestion().isEmpty() || !pregunta.getLinkQuestion().isBlank()){
            if(!pregunta.getLinkQuestion().matches(urlPattern)) {
			result.rejectValue("linkQuestion", "linkQuestion", "El enlace debe ser válido.");
		    }
        }

        if(pregunta.getTipo().contains("COMPLETA")){
            if(pregunta.getAnswer().isEmpty() || pregunta.getAnswer().isBlank()){
                result.rejectValue("answer", "answer", "Introduzca una respuesta.");
            }
        }

        if(pregunta.getTipo().contains("UNIQUE")){
            if(!pregunta.isCorrect1() && !pregunta.isCorrect2() && !pregunta.isCorrect3() && !pregunta.isCorrect4()){
                result.rejectValue("isCorrect1", "isCorrect1", "Una de las opciones debe ser la solución.");
            }
        }

        if(pregunta.getTipo().contains("UNIQUE") || pregunta.getTipo().contains("MULTI")){
            if((pregunta.getOption1().isEmpty() || pregunta.getOption1().isBlank()) && pregunta.isCorrect1()){
                result.rejectValue("option1", "option1", "No puede haber una solución vacía.");
            }
            if((pregunta.getOption2().isEmpty() || pregunta.getOption2().isBlank()) && pregunta.isCorrect2()){
                result.rejectValue("option2", "option2", "No puede haber una solución vacía.");
            }
            if((pregunta.getOption3().isEmpty() || pregunta.getOption3().isBlank()) && pregunta.isCorrect3()){
                result.rejectValue("option3", "option3", "No puede haber una solución vacía.");
            }
            if((pregunta.getOption4().isEmpty() || pregunta.getOption4().isBlank()) && pregunta.isCorrect4()){
                result.rejectValue("option4", "option4", "No puede haber una solución vacía.");
            }
            if((pregunta.getOption1().isEmpty() || pregunta.getOption1().isBlank()) && (!pregunta.getLinkOption1().isEmpty() || !pregunta.getLinkOption1().isBlank())){
                result.rejectValue("linkOption1", "linkOption1", "No puede haber una URL en una opción vacía.");
            }
            if((pregunta.getOption2().isEmpty() || pregunta.getOption2().isBlank()) && (!pregunta.getLinkOption2().isEmpty() || !pregunta.getLinkOption2().isBlank())){
                result.rejectValue("linkOption2", "linkOption2", "No puede haber una URL en una opción vacía.");
            }
            if((pregunta.getOption3().isEmpty() || pregunta.getOption3().isBlank()) && (!pregunta.getLinkOption3().isEmpty() || !pregunta.getLinkOption3().isBlank())){
                result.rejectValue("linkOption3", "linkOption3", "No puede haber una URL en una opción vacía.");
            }
            if((pregunta.getOption4().isEmpty() || pregunta.getOption4().isBlank()) && (!pregunta.getLinkOption4().isEmpty() || !pregunta.getLinkOption4().isBlank())){
                result.rejectValue("linkOption4", "linkOption4", "No puede haber una URL en una opción vacía.");
            }
            if(!pregunta.getLinkOption1().isEmpty() || !pregunta.getLinkOption1().isBlank()){
                if(!pregunta.getLinkOption1().matches(urlPattern)) {
                result.rejectValue("linkOption1", "linkOption1", "El enlace debe ser válido.");
                }
            }
            if(!pregunta.getLinkOption2().isEmpty() || !pregunta.getLinkOption2().isBlank()){
                if(!pregunta.getLinkOption2().matches(urlPattern)) {
                result.rejectValue("linkOption2", "linkOption2", "El enlace debe ser válido.");
                }
            }
            if(!pregunta.getLinkOption3().isEmpty() || !pregunta.getLinkOption3().isBlank()){
                if(!pregunta.getLinkOption3().matches(urlPattern)) {
                result.rejectValue("linkOption3", "linkOption3", "El enlace debe ser válido.");
                }
            }
            if(!pregunta.getLinkOption4().isEmpty() || !pregunta.getLinkOption4().isBlank()){
                if(!pregunta.getLinkOption4().matches(urlPattern)) {
                result.rejectValue("linkOption4", "linkOption4", "El enlace debe ser válido.");
                }
            }
        }

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

            this.ejercicioPreguntaService.eliminarLista(this.ejercicioPreguntaService.getEjerciciosPreguntaByPreguntaId(preguntaId));
            this.informePreguntaService.eliminarLista(this.informePreguntaService.getInformePreguntaByPreguntaId(preguntaId));
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
