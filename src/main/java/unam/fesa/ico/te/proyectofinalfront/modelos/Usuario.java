package unam.fesa.ico.te.proyectofinalfront.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data // Esto de Lombok te genera Getters, Setters, toString, etc.
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String correo;
    private String contraseña;
    private String rol;
    private String estado;
    private LocalDateTime fechaRegistro;
}
