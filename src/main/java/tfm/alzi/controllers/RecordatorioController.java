package tfm.alzi.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Recordatorio;
import tfm.alzi.services.RecordatorioService;
import tfm.alzi.services.UsuarioService;

@Controller
public class RecordatorioController {

    @Autowired 
    private RecordatorioService recordatorioService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping(value = "/crear-recordatorio")
    public String crearRecordatorioForm(final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() != null) {

			long id = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId();
            List<Recordatorio> recordatorios = this.recordatorioService.getRecordatoriosByID(id);
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

			Recordatorio recordatorio = new Recordatorio();
			model.addAttribute("recordatorio", recordatorio); 
			return "formNewRecordatorio";
		}
		return "login";
	}

	@PostMapping(value = "/crear-recordatorio")
    public String crearUsuario(@ModelAttribute("recordatorio") @Valid final Recordatorio recordatorio,
	//@RequestParam("fecha") LocalDateTime fecha,
	final BindingResult result,
	final Model model, HttpServletRequest request) {

		this.validarRecordatorio(recordatorio, result);

		if (result.hasErrors()) {

			long id = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId();
            List<Recordatorio> recordatorios = this.recordatorioService.getRecordatoriosByID(id);
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

			model.addAttribute("recordatorio", recordatorio);
			return "formNewRecordatorio";
		} else {
			recordatorio.setUsuarioId(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId());
			this.recordatorioService.crearRecordatorio(recordatorio);
		}
		return "redirect:/area-personal";
	}

	public void validarRecordatorio(final Recordatorio recordatorio, final BindingResult result) {

		if(recordatorio.getEtiqueta() == null | recordatorio.getEtiqueta().isEmpty() | recordatorio.getEtiqueta().isBlank()) {
			result.rejectValue("etiqueta", "etiqueta","Debes introducir una etiqueta.");
		}
		if(recordatorio.getDescripcion() == null | recordatorio.getDescripcion().isEmpty() | recordatorio.getDescripcion().isBlank()) {
			result.rejectValue("descripcion", "descripcion","Debes introducir una descripción.");
		}
		if(recordatorio.getFecha() == null){
			result.rejectValue("fecha", "fecha", "Debes introducir una fecha.");
		}
		if(recordatorio.getFecha() != null && recordatorio.getFecha().isBefore(LocalDateTime.now())){
			result.rejectValue("fecha", "fecha", "La fecha y hora introducida debe ser posterior a la actual.");
		}
	}

	@GetMapping(value = "/editar-recordatorio")
    public String editarRecordatorioForm(@RequestParam(value = "id")  Integer recordatorioId, 
	final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "index";
		} else {
			
			long id = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId();
            List<Recordatorio> recordatorios = this.recordatorioService.getRecordatoriosByID(id);
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
			
			Recordatorio recordatorio = this.recordatorioService.getRecordatorioById(recordatorioId);
			model.addAttribute("recordatorio", recordatorio);

			return "formEditRecordatorio";
		} 
	}

	@PostMapping(value = "/editar-recordatorio")
    public String editarRecordatorio(@ModelAttribute("recordatorio") @Valid final Recordatorio recordatorio,
	final BindingResult result, final Model model, final HttpServletRequest request) {

		this.validarRecordatorio(recordatorio, result);
		//System.out.println(recordatorioId);

		if (result.hasErrors()) {
			
			long id = this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId();
            List<Recordatorio> recordatorios = this.recordatorioService.getRecordatoriosByID(id);
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

			model.addAttribute("recordatorio", recordatorio);
			return "formEditRecordatorio"; 
		} else {
			//recordatorio.setUsuarioId(this.usuarioService.getUsuarioByDNI(request.getUserPrincipal().getName()).getId());
			this.recordatorioService.editarRecordatorio(recordatorio);
			return "redirect:/area-personal"; 
		}
	}
    
    @RequestMapping(value = "/borrar-recordatorio/{recordatorioId}")
	public String borrarRecordatorio(@PathVariable("recordatorioId") final long recordatorioId,
		Model model, HttpServletRequest request) {

		if (request.getUserPrincipal() != null) {
            recordatorioService.deleteRecordatorio(recordatorioId);
            model.addAttribute("recordatorioEliminado", "Recordatorio eliminado con éxito.");
			return "redirect:/area-personal";
		}
		return "login"; 
	}

}
