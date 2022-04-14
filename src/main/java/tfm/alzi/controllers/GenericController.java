package tfm.alzi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GenericController {

    @RequestMapping("/alzi")
    public String goToAlzi() {
        return "index";
    }
    
    @RequestMapping("/area-personal")
    public String goToAreaPersonal() {
        return "areaPersonal";
    }

    @RequestMapping("/entrenamiento")
    public String goToEntrenamiento() {
        return "entrenamiento";
    }

    @RequestMapping("/perfil")
    public String goToPerfil() {
        return "perfil";
    }

    @RequestMapping("/ajustes")
    public String goToAjustes() {
        return "ajustes";
    }

}
