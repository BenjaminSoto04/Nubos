package cl.nubos.desarrollador.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "desarrollador")
@Schema(description = "Modelo de desarrollador")
public class Desarrollador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del desarrollador", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del desarrollador", example = "Juan")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Estudio del desarrollador", example = "Team Cherry")
    private String estudio;

    @Column(nullable = false)
    @Schema(description = "Correo del desarrollador", example = "juarry143@gmail.com")
    private String correo;

    @Column(nullable = false)
    @Schema(description = "País del desarrollador", example = "Chile")
    private String país;

    @Column(nullable = false)
    @Schema(description = "Contraseña del desarrollador")
    private String contraseña;
}
