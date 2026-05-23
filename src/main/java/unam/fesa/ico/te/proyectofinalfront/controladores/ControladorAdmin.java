package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/administrador")
public class ControladorAdmin {

    private final RestTemplate restTemplate = new RestTemplate();

    // Lista estática en memoria para simular el almacenamiento durante este sprint
    private static final List<Map<String, String>> profesoresTemporales = new ArrayList<>();

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

        // Enviamos la lista de profesores y el contador dinámico a la vista
        model.addAttribute("listaProfesores", profesoresTemporales);
        model.addAttribute("totalProfesoresMemoria", profesoresTemporales.size());

        return "administrador/dashboard";
    }

    @PostMapping("/profesores")
    public String registrarProfesor(
            @RequestParam String correo,
            @RequestParam String contraseña,
            @RequestParam String nombre,
            @RequestParam String apellido_paterno,
            @RequestParam(required = false) String apellido_materno,
            @RequestParam(required = false) String telefono,
            RedirectAttributes redirectAttributes) {

        // Armamos la estructura en base a lo que pide la BD
        Map<String, String> nuevoProfe = new HashMap<>();
        String nombreCompleto = nombre + " " + apellido_paterno + (apellido_materno != null && !apellido_materno.isEmpty() ? " " + apellido_materno : "");
        nuevoProfe.put("nombre", nombreCompleto);
        nuevoProfe.put("correo", correo);
        nuevoProfe.put("telefono", telefono != null ? telefono : "");

        // Guardamos temporalmente en la lista
        profesoresTemporales.add(nuevoProfe);

        redirectAttributes.addFlashAttribute("exito", "El profesor " + nombreCompleto + " fue registrado correctamente en memoria.");

        return "redirect:/administrador/dashboard";
    }

    @PostMapping("/profesores/editar")
    public String editarProfesor(
            @RequestParam int indice,
            @RequestParam String nombre,
            @RequestParam String correo,
            RedirectAttributes redirectAttributes) {

        if (indice >= 0 && indice < profesoresTemporales.size()) {
            Map<String, String> profe = profesoresTemporales.get(indice);
            profe.put("nombre", nombre);
            profe.put("correo", correo);

            redirectAttributes.addFlashAttribute("exito", "Los datos de " + nombre + " fueron actualizados correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el índice del profesor a editar.");
        }

        return "redirect:/administrador/dashboard";
    }

    @PostMapping("/asignar")
    public String asignarProfesor(
            @RequestParam String id_profesor,
            @RequestParam String id_programa,
            RedirectAttributes redirectAttributes) {

        System.out.println("Asignando profesor ID: " + id_profesor + " al programa ID: " + id_programa);
        redirectAttributes.addFlashAttribute("exito", "Profesor asignado al programa con éxito (Simulación).");

        return "redirect:/administrador/dashboard";
    }
}