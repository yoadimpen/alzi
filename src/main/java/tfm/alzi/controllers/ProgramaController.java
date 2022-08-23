package tfm.alzi.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import tfm.alzi.models.Ejercicio;
import tfm.alzi.models.Programa;
import tfm.alzi.models.ProgramaEjercicio;
import tfm.alzi.models.Usuario;
import tfm.alzi.services.EjercicioService;
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

}