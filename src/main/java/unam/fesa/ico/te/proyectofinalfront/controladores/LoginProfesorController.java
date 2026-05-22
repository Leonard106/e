package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/serviciosocial")
public class LoginProfesorController {

    @GetMapping("/login/profesor")
    public String mostrarLoginProfesor(){
        return "login-profesor";
    }

    @PostMapping("/login/profesor")
    public String procesarLoginProfesor(@RequestParam String correo,
                                        @RequestParam String password,
                                        Model model) {

        if(correo.equals("profesor@unam.mx") && password.equals("1234")) {

            model.addAttribute("mensaje", "Login exitoso");
            return "login-profesor";

        } else {

            model.addAttribute("error", "Credenciales inválidas");
            return "login-profesor";
        }
    }
}