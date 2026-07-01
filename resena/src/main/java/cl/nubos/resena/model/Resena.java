package cl.nubos.resena.model;

import java.time.LocalDate;

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
@Table(name = "resena")
@Schema(description = "Modelo de resena")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la reseña", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Id único del usuario", example = "1")
    private Integer usuario;

    @Column(nullable = false)
    @Schema(description = "Id único del videojuego", example = "1")
    private Integer videojuego;

    @Column(nullable = false)
    @Schema(description = "Calificación de la reseña", example = "5")
    private Integer calificacion;

    @Column(nullable = false, length = 1000)
    @Schema(description = "Comentario de la reseña", example = "Excelente juego")
    private String comentario;

    @Column(nullable = false)
    @Schema(description = "Fecha de la reseña", example = "2026-06-30")
    private LocalDate fechaResena;

    @Column(nullable = false)
    @Schema(description = "Si el usuario recomienda o no el videojuego", example = "true")
    private Boolean recomendado;
}
