package cl.nubos.categoria.model;

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
@Table(name = "categoria")
@Schema(description = "Modelo de categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la categoria", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la categoria", example = "Acción")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Descripción de la categoria", example = "Juegos llenos de adrenalina")
    private String descripcion;
}
