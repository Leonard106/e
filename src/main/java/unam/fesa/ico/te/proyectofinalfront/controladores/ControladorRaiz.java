package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorRaiz {

    @GetMapping("/")
    public String redireccionarInicio() {
        return "redirect:/serviciosocial/pag_inicio";
    }
}