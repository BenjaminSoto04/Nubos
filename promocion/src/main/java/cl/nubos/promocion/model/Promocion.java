package cl.nubos.promocion.model;

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
@Table(name = "promocion")
@Schema(description = "Modelo de promoción")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado de la promoción", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "ID del videojuego al que se le aplica la promoción", example = "1")
    private Integer videojuego;

    @Column(nullable = false)
    @Schema(description = "Porcentaje de descuento a aplicar", example = "20")
    private Integer porcentajeDescuento;

    @Column(nullable = false)
    @Schema(description = "Fecha de inicio de la promoción", example = "2026-06-30")
    private LocalDate fechaInicio;

    @Column(nullable = false)
    @Schema(description = "Fecha de fin de la promoción", example = "2026-07-30")
    private LocalDate fechaFin;

    @Column(nullable = false)
    @Schema(description = "Código único de la promoción", example = "PROMO20")
    private String codigoPromocional;

    @Column(nullable = false)
    @Schema(description = "Indica si la promoción está activa", example = "true")
    private Boolean activa;
}
