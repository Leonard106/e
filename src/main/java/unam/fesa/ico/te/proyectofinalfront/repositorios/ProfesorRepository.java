package unam.fesa.ico.te.proyectofinalfront.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unam.fesa.ico.te.proyectofinalfront.modelos.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
}
