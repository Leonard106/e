package unam.fesa.ico.te.proyectofinalfront.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import unam.fesa.ico.te.proyectofinalfront.modelos.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
