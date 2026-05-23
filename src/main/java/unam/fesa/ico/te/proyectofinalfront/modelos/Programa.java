package unam.fesa.ico.te.proyectofinalfront.modelos;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Table(name = "programas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Programa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrograma;
    private String nombre;
    private String descripcion;
    private String area;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Integer numeroHoras;
    private String estado;

}
