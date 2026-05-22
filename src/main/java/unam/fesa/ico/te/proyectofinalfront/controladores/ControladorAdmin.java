package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/administrador")
public class ControladorAdmin {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        String backendUrl = "http://localhost:8080/api/dashboard/admin";

        try {
            Object datosDashboard = restTemplate.getForObject(backendUrl, Object.class);
            model.addAttribute("datos", datosDashboard);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar las estadísticas globales.");
            model.addAttribute("datos", null);
        }

        return "administrador/dashboard";
    }
}