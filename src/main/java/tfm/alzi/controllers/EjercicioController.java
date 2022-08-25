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

}
