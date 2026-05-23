package unam.fesa.ico.te.proyectofinalfront.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProfesor;

    // Relación con la tabla usuarios
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private LocalDateTime fechaRegistro;
}