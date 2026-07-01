package cl.nubos.soporte.model;

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
@Table(name = "soporte")
@Schema(description = "Modelo de soporte")
public class Soporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del soporte", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Título del soporte", example = "Problema con el juego")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descripción del problema", example = "El juego no se inicia")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Fecha del reporte", example = "2022-01-01")
    private LocalDate fechaReporte;

    @Column(nullable = false)
    @Schema(description = "Estado del soporte", example = "Pendiente")
    private String estado;
}
