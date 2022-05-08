package tfm.alzi.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.RecordatorioService;
import tfm.alzi.services.UsuarioService;

@Controller
public class GenericController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecordatorioService repositorioService;

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
            List<Recordatorio> recordatorios = this.repositorioService.getRecordatoriosByID(id);
			model.addAttribute("recordatorios", recordatorios);
            String jsonRecordatorios = "[";
            for (int i=0; i<recordatorios.size(); i++) {
                /*Map jo = new HashMap();
                jo.put("title", recordatorios.get(i).getEtiqueta());
                jo.put("start", recordatorios.get(i).getFecha().getYear() +
                "-" + recordatorios.get(i).getFecha().getMonthValue() +
                "-" + recordatorios.get(i).getFecha().getDayOfMonth());
                //jsonRecordatorios.concat(jo.toString()).concat(",");
                System.out.println(jo.toString());*/
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
            System.out.println(jsonRecordatorios);
            //System.out.println(jsonRecordatorios.substring(1, jsonRecordatorios.length()-1));
            model.addAttribute("jsonRecordatorios", jsonRecordatorios);
            
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
