package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/serviciosocial") // Se quitó la barra final para evitar conflictos de rutas
public class ControladorPrincipal {

    @GetMapping("/pag_inicio") // Se quitó la barra final
    public String muestraInicio(){
        return "pag_inicio";
    }

    @GetMapping("/login/admin")
    public String mostrarLoginAdmin() {
        return "login_admin";
    }

    @PostMapping("/login/admin")
    public String procesarLoginAdmin(
            @RequestParam("usuario") String usuario,
            @RequestParam("password") String password,
            Model model) {

        // Simulación de validación
        if ("admin".equals(usuario) && "12345".equals(password)) {
            // CORRECCIÓN: Redirige al controlador "ControladorAdmin" que creamos antes
            return "redirect:/administrador/dashboard";
        } else {
            model.addAttribute("error", "Credenciales incorrectas. Verifica tu usuario y contraseña.");
            return "login_admin";
        }
    }

    // El método GET de dashboard temporal se puede eliminar,
    // ya que ControladorAdmin se encargará de esa ruta de aquí en adelante.
}