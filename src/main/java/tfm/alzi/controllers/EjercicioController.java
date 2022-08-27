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
import tfm.alzi.models.Pregunta;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.EjercicioPreguntaService;
import tfm.alzi.services.EjercicioService;
import tfm.alzi.services.InformeEjercicioService;
import tfm.alzi.services.InformePreguntaService;
import tfm.alzi.services.InformeProgramaService;
import tfm.alzi.services.PreguntaService;
import tfm.alzi.services.ProgramaEjercicioService;
import tfm.alzi.services.UsuarioService;

@Controller
public class EjercicioController {
    
    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private EjercicioPreguntaService ejercicioPreguntaService;

    @Autowired
    private ProgramaEjercicioService programaEjercicioService;

    @Autowired
    private InformePreguntaService informePreguntaService;

    @Autowired
    private InformeEjercicioService informeEjercicioService;

    @Autowired
    private InformeProgramaService informeProgramaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/do-ejercicio")
    public String doEjercicio(@RequestParam(value = "programaId")  Integer programaId,
    @RequestParam(value = "ejercicioId")  Integer ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = ejercicioService.getEjercicioById(ejercicioId);
            model.addAttribute("ejercicio", e);

            //Preguntas
            List<EjercicioPregunta> ejercicioPregunta = this.ejercicioPreguntaService.getEjercicioPreguntaByEjercicioId(ejercicioId);
            List<Pregunta> preguntas = new ArrayList<Pregunta>();
            for (EjercicioPregunta ep: ejercicioPregunta){
                preguntas.add(this.preguntaService.getPreguntaById(ep.getPreguntaId()));
            }

            if(preguntas.size()>=1){
                model.addAttribute("pregunta1",preguntas.get(0));
                if(preguntas.size()>=2){
                    model.addAttribute("pregunta2",preguntas.get(1));
                    if(preguntas.size()>=3){
                        model.addAttribute("pregunta3",preguntas.get(2));
                        if(preguntas.size()>=4){
                            model.addAttribute("pregunta4",preguntas.get(3));
                        }
                    }
                }
            }
            
            Principal principal = request.getUserPrincipal();
            String dni = principal.getName();
            Usuario usuario = this.usuarioService.getUsuarioByDNI(dni);
            //Informes
            List<InformePregunta> informes = this.informePreguntaService.getInformesPreguntaCustom(programaId, ejercicioId, usuario.getId());

            if(informes.size()>=1){
                model.addAttribute("informe1",informes.get(0));
                if(informes.size()>=2){
                    model.addAttribute("informe2",informes.get(1));
                    if(informes.size()>=3){
                        model.addAttribute("informe3",informes.get(2));
                        if(informes.size()>=4){
                            model.addAttribute("informe4",informes.get(3));
                        }
                    }
                }
            }

            model.addAttribute("programaId", programaId);
            model.addAttribute("ejercicioId", ejercicioId);

            return "usuario/doEjercicio";
        }
        return "login";
    }

    @PostMapping(value = "/do-ejercicio")
    public String entregaEjercicio(@RequestParam(value = "programaId")  Integer programaId,
    @RequestParam(value = "ejercicioId")  Integer ejercicioId,
    @RequestParam("respuesta1") String respuesta1,
    @RequestParam("respuesta2") String respuesta2,
    @RequestParam("respuesta3") String respuesta3,
    @RequestParam("respuesta4") String respuesta4,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){

            //Respuestas
            List<String> respuestas = new ArrayList<String>();
            respuestas.add(respuesta1);
            respuestas.add(respuesta2);
            respuestas.add(respuesta3);
            respuestas.add(respuesta4);

            //Preguntas
            List<EjercicioPregunta> ejercicioPregunta = this.ejercicioPreguntaService.getEjercicioPreguntaByEjercicioId(ejercicioId);
            List<Pregunta> preguntas = new ArrayList<Pregunta>();
            for (EjercicioPregunta ep: ejercicioPregunta){
                preguntas.add(this.preguntaService.getPreguntaById(ep.getPreguntaId()));
            }

            Principal principal = request.getUserPrincipal();
            String dni = principal.getName();
            Usuario usuario = this.usuarioService.getUsuarioByDNI(dni);
            //Informes
            List<InformePregunta> informes = this.informePreguntaService.getInformesPreguntaCustom(programaId, ejercicioId, usuario.getId());

            for (int i=0; i < preguntas.size(); i++){
                actualizarInformePregunta(preguntas.get(i), informes.get(i), respuestas.get(i));
            }

            InformeEjercicio informeEjercicio = this.informeEjercicioService.getInformeEjercicioCustom(programaId, ejercicioId, usuario.getId());

            InformePrograma informePrograma = this.informeProgramaService.getInformeProgramaCustom(programaId, usuario.getId());
            List<InformeEjercicio> informesEjs = this.informeEjercicioService.getInformesEjercicioCustom(programaId, usuario.getId());

            actualizarInformeEjercicio(informeEjercicio, informes);
            actualizarInformePrograma(informePrograma, informesEjs);

            return "redirect:/show-programa?id=" + programaId;
        }
        return "login";
    }

    private void actualizarInformePrograma(InformePrograma informePrograma, List<InformeEjercicio> informesEjs) {

        Integer total = informesEjs.size();
        Integer finalizados = 0;

        Integer aciertosAcum = 0;
        Integer fallosAcum = 0;

        for (int i=0; i<informesEjs.size(); i++){
            if(informesEjs.get(i).esFinalizado() == true){
                finalizados++;
            }

            aciertosAcum += informesEjs.get(i).getAciertos();
            fallosAcum += informesEjs.get(i).getFallos();
        }

        double res = finalizados/(double)total*100;
        Integer value = (int)res;

        informePrograma.setProgreso(value);
        informePrograma.setAciertos(aciertosAcum);
        informePrograma.setFallos(fallosAcum);

        informeProgramaService.editarInformePrograma(informePrograma);

    }

    private void actualizarInformeEjercicio(InformeEjercicio informeEjercicio, List<InformePregunta> informes) {

        Integer aciertos = 0;
        Integer fallos = 0;

        for (int i=0; i<informes.size(); i++){
            if(informes.get(i).getResultado() == true){
                aciertos++;
            } else {
                fallos++;
            }
        }

        informeEjercicio.setAciertos(aciertos);
        informeEjercicio.setFallos(fallos);
        informeEjercicio.esFinalizado(true);

        informeEjercicioService.editarInformeEjercicio(informeEjercicio);

    }

    private void actualizarInformePregunta(Pregunta pregunta, InformePregunta informePregunta, String respuesta) {

        informePregunta.setRespuesta(respuesta);

        switch(pregunta.getTipo()) {
            case "MULTI":
                String[] res_real = pregunta.getAnswer().split(";");
                String[] res = respuesta.split(",");

                Boolean sonIguales = true;

                if(res_real.length != res.length){
                    sonIguales = false;
                } else {
                    for (int i=0; i < res_real.length; i++){
                        sonIguales = sonIguales && res_real[i].equals(res[i]);
                    }
                }

                informePregunta.setResultado(sonIguales);
                informePreguntaService.editarInformePregunta(informePregunta);

                break;
            default:
                informePregunta.setResultado(pregunta.getAnswer().equals(respuesta));
                
                informePreguntaService.editarInformePregunta(informePregunta);
        }
    }

    @GetMapping(value = "/crear-ejercicio") 
    public String crearEjercicioForm(Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = new Ejercicio();

            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());
            e.setUsuarioId(u.getId());
            e.setPublico(false);

            model.addAttribute("ejercicio", e);

            List<Pregunta> preguntas = this.preguntaService.getAllPublicPreguntas();
            model.addAttribute("preguntas", preguntas);

            return "especialista/formNewEjercicio";
        }
        return "login";
    }

    @GetMapping(value = "/ejercicio") 
    public String showEjercicioEspecialista(@RequestParam(value = "id") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e  = this.ejercicioService.getEjercicioById(ejercicioId);
            model.addAttribute("ejercicio", e);

            List<EjercicioPregunta> ejericioPreguntas = this.ejercicioPreguntaService.findByEjercicioId(ejercicioId);
            List<Pregunta> preguntas = new ArrayList<>();
            for (EjercicioPregunta ep: ejericioPreguntas){
                preguntas.add(this.preguntaService.getPreguntaById(ep.getPreguntaId()));
            }
            model.addAttribute("preguntas", preguntas);

            return "especialista/showEjercicio";
        }
        return "login";
    }

    @PostMapping(value = "/crear-ejercicio")
    public String crearEjercicio(@ModelAttribute("ejercicio") @Valid final Ejercicio ejercicio,
    final BindingResult result, @ModelAttribute("preguntasArray") String preguntas,
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            this.validarEjercicio(result, ejercicio);

            if(result.hasErrors()) {
                model.addAttribute("ejercicio", ejercicio);
                List<Pregunta> pr = this.preguntaService.getAllPublicPreguntas();
                model.addAttribute("preguntas", pr);

                return "especialista/formNewEjercicio";
            } else {
                String[] arrayPreguntas = preguntas.split(",");
                if(arrayPreguntas.length >= 2){
                    ejercicio.setPublico(true);
                }
                this.ejercicioService.crearEjercicio(ejercicio);
                for (String e:arrayPreguntas){
                    EjercicioPregunta rel = new EjercicioPregunta();
                    rel.setEjercicioId(ejercicio.getId());
                    rel.setPreguntaId(Long.valueOf(e));
                    this.ejercicioPreguntaService.crearRelacion(rel);
                }
            }

            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("ejercicioCreado", "Ejercicio creado con éxito!");

            return "ejercicios";
        
        }
        return "login";
		
	}

    private void validarEjercicio(BindingResult result, final Ejercicio ejercicio) {

        if(ejercicio.getTitulo().isEmpty() || ejercicio.getTitulo().isBlank()) {
			result.rejectValue("titulo","titulo","Introduzca un título.");
		}

        if(ejercicio.getDescripcion().isEmpty() || ejercicio.getDescripcion().isBlank()) {
			result.rejectValue("descripcion", "descripcion","Introduzca una descripción.");
		}

        if(ejercicio.getDuracion() == null){
            result.rejectValue("duracion", "duracion", "Introduzca una duración.");
        }

        if(ejercicio.getDuracion() != null){
            if(ejercicio.getDuracion() <= 0)
            result.rejectValue("duracion", "duracion", "La duración debe ser mayor que 0 minutos.");
        }

    }

    @GetMapping(value = "/ocultar-ejercicio") 
    public String hideEjercicio(@RequestParam(value = "id") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = ejercicioService.getEjercicioById(ejercicioId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(e.getUsuarioId() == u.getId()){
                e.setPublico(false);
                this.ejercicioService.editarEjercicio(e);
            }

            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("ejercicioOcultado", "Ejercicio ocultado con éxito!");

            return "ejercicios";
        }
        return "login";
    }

    @GetMapping(value = "/publicar-ejercicio") 
    public String publishEjercicio(@RequestParam(value = "id") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = ejercicioService.getEjercicioById(ejercicioId);
            Usuario u = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());

            if(e.getUsuarioId() == u.getId()){
                e.setPublico(true);
                this.ejercicioService.editarEjercicio(e);
            }

            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("ejercicioPublicado", "Ejercicio publicado con éxito!");

            return "ejercicios";
        }
        return "login";
    }

    @GetMapping(value = "/editar-ejercicio") 
    public String editarEjercicioForm(@RequestParam(value = "id") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            Ejercicio e = this.ejercicioService.getEjercicioById(ejercicioId);

            model.addAttribute("ejercicio", e);

            return "especialista/formEditEjercicio";
        }
        return "login";
    }

    @PostMapping(value = "/editar-ejercicio")
    public String editarEjercicio(@ModelAttribute("id") String ejercicioId,
    @ModelAttribute("ejercicio") @Valid final Ejercicio ejercicio, final BindingResult result, 
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            ejercicio.setId(Long.valueOf(ejercicioId));

            this.validarEjercicio(result, ejercicio);

            if(result.hasErrors()) {
                model.addAttribute("ejercicio", ejercicio);
                return "especialista/formEditEjercicio";
            } else {
                this.ejercicioService.editarEjercicio(ejercicio);
            }

            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("ejercicioEditado", "Ejercicio editado con éxito!");

            return "ejercicios";
        
        }
        return "login";
		
	}

    @GetMapping(value = "/actualizar-preguntas") 
    public String actualizarPreguntasForm(@RequestParam(value = "ejercicioId") final long ejercicioId,
    Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            List<EjercicioPregunta> ls = this.ejercicioPreguntaService.getEjercicioPreguntaByEjercicioId(ejercicioId);
            List<Pregunta> preguntas = this.preguntaService.getAllPublicPreguntas();

            List<Pregunta> preguntasCurrent = new ArrayList<>();
            for (EjercicioPregunta ep: ls){
                preguntasCurrent.add(this.preguntaService.getPreguntaById(ep.getPreguntaId()));
            }
            model.addAttribute("preguntas", preguntas);

            model.addAttribute("preguntasCurrent", preguntasCurrent);
            model.addAttribute("preguntas", preguntas);
            model.addAttribute("ejercicioId", ejercicioId);

            return "especialista/actualizarPreguntas";
        }
        return "login";
    }

    @PostMapping(value = "/actualizar-preguntas")
    public String actualizarPreguntas(@ModelAttribute("ejercicioId") long ejercicioId,
    @ModelAttribute("preguntasArray") String preguntas,
    final Model model, HttpServletRequest request) {

        if(request.getUserPrincipal() != null){

            List<EjercicioPregunta> preguntasCurrent = this.ejercicioPreguntaService.getEjercicioPreguntaByEjercicioId(ejercicioId);
            List<Long> ids = new ArrayList<>();
            
            for(EjercicioPregunta ep: preguntasCurrent){
                ids.add(ep.getPreguntaId());
            }

            String[] arrayPregunta = preguntas.split(",");
            List<Long> idsNew = new ArrayList<>();
            for(String s: arrayPregunta){
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
                EjercicioPregunta rel = this.ejercicioPreguntaService.findByEjercicioIdPreguntaId(ejercicioId, r);
                this.ejercicioPreguntaService.eliminarRelacion(rel);
            }
            
            for (Long c:toCreate){
                EjercicioPregunta rel = new EjercicioPregunta();
                rel.setEjercicioId(ejercicioId);
                rel.setPreguntaId(c);
                this.ejercicioPreguntaService.crearRelacion(rel);
            }

            System.out.println(toRemove);
            System.out.println(toCreate);

            return "redirect:/ejercicio?id=" + ejercicioId;
        
        }
        return "login";
		
	}

    @RequestMapping(value = "/borrar-ejercicio")
    public String eliminarPrograma(@RequestParam(value = "id") final long ejercicioId,
    Model model, HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {

            this.ejercicioPreguntaService.eliminarLista(this.ejercicioPreguntaService.findByEjercicioId(ejercicioId));
            this.informeEjercicioService.eliminarLista(this.informeEjercicioService.findByEjercicioId(ejercicioId));
            this.informePreguntaService.eliminarLista(this.informePreguntaService.findByEjercicioId(ejercicioId));
            this.programaEjercicioService.eliminarLista(this.programaEjercicioService.findByEjercicioId(ejercicioId));
            this.ejercicioService.eliminarEjercicio(ejercicioId);

            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));
            model.addAttribute("ejercicioEliminado", "Ejercicio eliminado con éxito!");

			return "ejercicios";
		} else {
			return "login";
		}
	}

}
