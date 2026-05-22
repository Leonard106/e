package unam.fesa.ico.te.proyectofinalfront.controladores;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class RestControladorAdmin {

    private static final Map<String, Object> memoriaDashboard = new HashMap<>();

    static {
        Map<String, Object> prog = new HashMap<>();
        prog.put("activos", 0L);
        prog.put("inactivos", 0L);
        prog.put("finalizados", 0L);
        memoriaDashboard.put("programas", prog);

        Map<String, Object> user = new HashMap<>();
        user.put("totalProfesores", 0L);
        user.put("totalAlumnos", 0L);
        memoriaDashboard.put("usuarios", user);

        memoriaDashboard.put("avanceHorasPorPrograma", new ArrayList<>());
    }

    @GetMapping("/admin")
    public Map<String, Object> obtenerDatosSimulados() {
        return memoriaDashboard;
    }

    @PostMapping("/admin")
    public String guardarDatosDashboard(@RequestBody Map<String, Object> nuevosDatos) {
        if (nuevosDatos.containsKey("programas")) memoriaDashboard.put("programas", nuevosDatos.get("programas"));
        if (nuevosDatos.containsKey("usuarios")) memoriaDashboard.put("usuarios", nuevosDatos.get("usuarios"));
        if (nuevosDatos.containsKey("avanceHorasPorPrograma")) memoriaDashboard.put("avanceHorasPorPrograma", nuevosDatos.get("avanceHorasPorPrograma"));
        return "Estructura del Dashboard actualizada y guardada con éxito.";
    }

    @PutMapping("/admin")
    public String actualizarCampoEspecifico(@RequestParam String clave, @RequestBody Object valor) {
        memoriaDashboard.put(clave, valor);
        return "Campo '" + clave + "' actualizado con éxito.";
    }

    @DeleteMapping("/admin")
    public String reiniciarDashboard() {
        ((Map<?, ?>) memoriaDashboard.get("programas")).clear();
        ((Map<?, ?>) memoriaDashboard.get("usuarios")).clear();
        ((ArrayList<?>) memoriaDashboard.get("avanceHorasPorPrograma")).clear();

        Map<String, Object> prog = (Map<String, Object>) memoriaDashboard.get("programas");
        prog.put("activos", 0L);
        prog.put("inactivos", 0L);
        prog.put("finalizados", 0L);

        Map<String, Object> user = (Map<String, Object>) memoriaDashboard.get("usuarios");
        user.put("totalProfesores", 0L);
        user.put("totalAlumnos", 0L);

        return "Memoria del Dashboard restablecida a ceros.";
    }
}