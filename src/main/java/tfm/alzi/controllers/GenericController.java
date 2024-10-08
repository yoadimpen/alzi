package tfm.alzi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.ParticipantePrograma;
import tfm.alzi.models.Programa;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.models.Usuario;
import tfm.alzi.models.UsuarioCuidador;
import tfm.alzi.services.EjercicioService;
import tfm.alzi.services.ParticipanteProgramaService;
import tfm.alzi.services.ProgramaService;
import tfm.alzi.services.RecordatorioService;
import tfm.alzi.services.UsuarioCuidadorService;
import tfm.alzi.services.UsuarioService;

@Controller
public class GenericController { 

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecordatorioService recordatorioService;

    @Autowired
    private ParticipanteProgramaService participanteProgramaService;

    @Autowired
    private ProgramaService programaService;

    @Autowired
    private EjercicioService ejercicioService;

    @Autowired
    private UsuarioCuidadorService usuarioCuidadorService;

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
            long id = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId();
            List<Recordatorio> recordatorios = this.recordatorioService.getRecordatoriosByID(id);
			model.addAttribute("recordatorios", recordatorios);
            String jsonRecordatorios = "[";
            for (int i=0; i<recordatorios.size(); i++) {
                jsonRecordatorios = jsonRecordatorios.concat("{\"title\": \"" + recordatorios.get(i).getEtiqueta() +
                "\", \"start\": \"" + recordatorios.get(i).getFecha().getYear() +
                "-" + String.format("%02d", recordatorios.get(i).getFecha().getMonthValue()) + 
                "-" + String.format("%02d", recordatorios.get(i).getFecha().getDayOfMonth()) +
                "\", \"end\": \"" + recordatorios.get(i).getFecha().getYear() + 
                "-" + String.format("%02d", recordatorios.get(i).getFecha().getMonthValue()) + 
                "-" + String.format("%02d", recordatorios.get(i).getFecha().getDayOfMonth()) + "\"}");
                if(i < recordatorios.size() - 1) {
                    jsonRecordatorios = jsonRecordatorios.concat(",");
                } 
            }
            jsonRecordatorios = jsonRecordatorios.concat("]");
            model.addAttribute("jsonRecordatorios", jsonRecordatorios);
            
            return "areaPersonal";
		} else {
            return "login";
        } 
    }

    @RequestMapping("/entrenamiento")
    public String goToEntrenamiento(final Model model, final HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            Usuario usuario = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName());
            if(usuario.getRoles().equals("USUARIO")){
                List<ParticipantePrograma> suscripciones = this.participanteProgramaService.getSuscripcionesByID(usuario.getId());
                List<Programa> programas = new ArrayList<Programa>();
                for (ParticipantePrograma s:suscripciones){
                    programas.add(this.programaService.getProgramaById(s.getProgramaId()));
                } 
                model.addAttribute("programas", programas);
            }
            return "usuario/showPlan";
		} else {
            return "login";
        }
    }

    @GetMapping(value = "/programas")
    public String getProgramas(final Model model,
    final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
            model.addAttribute("programas", this.programaService.getAllPublicProgramas());
            model.addAttribute("programasPriv", this.programaService.getMyPrivateProgramas(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));

            return "programas";
		} else {
            return "login";
        } 
	}

    @GetMapping(value = "/ejercicios")
    public String getEjercicios(final Model model,
    final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {
            model.addAttribute("ejercicios", this.ejercicioService.getAllPublicEjercicios());
            model.addAttribute("ejerciciosPriv", this.ejercicioService.getMyPrivateEjercicios(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId()));

            return "ejercicios";
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
            model.addAttribute("isEdited", false);

            UsuarioCuidador uc = this.usuarioCuidadorService.findByUsuarioId(usuario.getId());
            if(uc != null){
                model.addAttribute("hayCuidador", true);
                Usuario cuidador = this.usuarioService.getUsuarioById(uc.getCuidadorId());
                model.addAttribute("cuidador", cuidador.getNombre() + " " + cuidador.getApellidos());
            }

			return "perfil";
		} else {
            return "login";
        }
    }

}
