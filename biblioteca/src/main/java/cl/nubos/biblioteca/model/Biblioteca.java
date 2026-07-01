package cl.nubos.biblioteca.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "biblioteca")
@Schema(description = "Modelo de biblioteca")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la biblioteca", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Id único del usuario", example = "1")
    private Integer usuario;

    @Column(nullable = false)
    @Schema(description = "Id único del videojuego", example = "1")
    private Integer videojuego;

    @Column(nullable = false)
    @Schema(description = "Fecha de adquisición", example = "2026-06-30")
    private LocalDate fechaAdquisicion;

    @Column(nullable = false)
    @Schema(description = "Horas jugadas", example = "10")
    private Integer horasJugadas;

    @Column(nullable = true)
    @Schema(description = "Fecha y hora de la última sesión", example = "2026-06-30T12:00:00")
    private LocalDateTime ultimaSesion;
}
