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
    private static final List<Map<String, String>> programasTemporales = new ArrayList<>(); // ← agregada

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
        model.addAttribute("listaProgramas", programasTemporales);

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

    @PostMapping("/programa")
    public String registrarPrograma(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String area,
            @RequestParam String fechaInicio,
            @RequestParam String fechaTermino,
            @RequestParam String numeroHoras,
            @RequestParam String estado,
            RedirectAttributes redirectAttributes) {

        Map<String, String> nuevoPrograma = new HashMap<>();
        nuevoPrograma.put("nombre", nombre);
        nuevoPrograma.put("descripcion", descripcion);
        nuevoPrograma.put("area", area);
        nuevoPrograma.put("fechaInicio", fechaInicio);
        nuevoPrograma.put("fechaTermino", fechaTermino);
        nuevoPrograma.put("numeroHoras", numeroHoras);
        nuevoPrograma.put("estado", estado);
        programasTemporales.add(nuevoPrograma);

        redirectAttributes.addFlashAttribute("exito", "El programa " + nombre + " fue registrado correctamente.");
        return "redirect:/administrador/dashboard";
    }


    @PostMapping("/programa/editar")
    public String editarPrograma(
            @RequestParam int indice,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String area,
            @RequestParam String fechaInicio,
            @RequestParam String fechaTermino,
            @RequestParam String numeroHoras,
            @RequestParam String estado,
            RedirectAttributes redirectAttributes) {

        if (indice >= 0 && indice < programasTemporales.size()) {
            Map<String, String> programa = programasTemporales.get(indice);
            programa.put("nombre", nombre);
            programa.put("descripcion", descripcion);
            programa.put("area", area);
            programa.put("fechaInicio", fechaInicio);
            programa.put("fechaTermino", fechaTermino);
            programa.put("numeroHoras", numeroHoras);
            programa.put("estado", estado);

            redirectAttributes.addFlashAttribute("exito", "El programa " + nombre + " fue actualizado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo encontrar el programa a editar.");
        }

        return "redirect:/administrador/dashboard";
    }

    @PostMapping("/asignar")
    public String asignarProfesor(
            @RequestParam int indiceProfesor,
            @RequestParam int indicePrograma,
            RedirectAttributes redirectAttributes) {

        if (indiceProfesor >= 0 && indiceProfesor < profesoresTemporales.size()
                && indicePrograma >= 0 && indicePrograma < programasTemporales.size()) {

            Map<String, String> profesor = profesoresTemporales.get(indiceProfesor);
            Map<String, String> programa = programasTemporales.get(indicePrograma);

            String nombreProfesor = profesor.get("nombre");

            // Si el programa ya tiene profesores, agrega al final separado por coma
            // Si no tiene ninguno, empieza con el primero
            String profesoresActuales = programa.get("profesores");
            if (profesoresActuales == null || profesoresActuales.isEmpty()) {
                programa.put("profesores", nombreProfesor);
            } else {
                // Evitar duplicados
                if (!profesoresActuales.contains(nombreProfesor)) {
                    programa.put("profesores", profesoresActuales + ", " + nombreProfesor);
                } else {
                    redirectAttributes.addFlashAttribute("error", "Ese profesor ya está asignado a este programa.");
                    return "redirect:/administrador/dashboard";
                }
            }

            redirectAttributes.addFlashAttribute("exito",
                    "El profesor " + nombreProfesor + " fue asignado al programa \"" + programa.get("nombre") + "\" correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el profesor o programa seleccionado.");
        }

        return "redirect:/administrador/dashboard";
    }
}