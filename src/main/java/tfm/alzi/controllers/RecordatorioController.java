package tfm.alzi.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	@GetMapping(value = "/editar-recordatorio/{recordatorioId}")
    public String editarRecordatorioForm(@PathVariable("recordatorioId") final long recordatorioId, 
	final Model model, final HttpServletRequest request) {
		if (request.getUserPrincipal() == null) {
			return "index";
		} else {
			Recordatorio recordatorio = this.recordatorioService.getRecordatorioById(recordatorioId);
			model.addAttribute("recordatorio", recordatorio);
			//model.addAttribute("id", recordatorio.getId());
			return "formEditRecordatorio";
		} 
	}

	@PostMapping(value = "/editar-recordatorio")
    public String editarRecordatorio(@ModelAttribute("recordatorio") @Valid final Recordatorio recordatorio,
	final BindingResult result, final Model model, final HttpServletRequest request) {

		this.validarRecordatorio(recordatorio, result);
		//System.out.println(recordatorioId);

		if (result.hasErrors()) {
			//recordatorio.setId(recordatorioId);
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
			return "areaPersonal";
		}
		return "login";
	}

}
