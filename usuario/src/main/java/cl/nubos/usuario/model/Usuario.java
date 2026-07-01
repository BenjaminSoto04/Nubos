package cl.nubos.usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Schema(description = "Modelo de usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del usuario", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Edad del usuario", example = "18")
    private Integer edad;

    @Column(nullable = false)
    @Schema(description = "Correo del usuario", example = "juarry143@gmail.com")
    private String correo;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "12345678")
    private String contraseña;
}
