package cl.nubos.videojuego.model;

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
@Table(name = "videojuego")
@Schema(description = "Modelo de videojuego")
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del videojuego", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Titulo del videojuego", example = "FIFA 22")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descripción del videojuego", example = "FIFA 22 es un videojuego de fútbol desarrollado por EA Vancouver")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Precio del videojuego", example = "69.99")
    private Double precio;

    @Column(nullable = false)
    @Schema(description = "Fecha de lanzamiento del videojuego", example = "2022-01-01")
    private LocalDate fechaLanzamiento;

    @Column(nullable = false)
    @Schema(description = "Información del desarrollador del videojuego", example = "1")
    private Integer desarrollador;

    @Column(nullable = false)
    @Schema(description = "Información de la categoría del videojuego", example = "1")
    private Integer categoria;

    @Column(nullable = false)
    @Schema(description = "Clasificación de edad del videojuego", example = "Todo Publico")
    private String clasificacionEdad;
}
