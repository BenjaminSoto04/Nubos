package cl.nubos.carrito.model;

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
@Table(name = "carrito")
@Schema(description = "Modelo de carrito")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único del carrito", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Información del usuario", example = "1")
    private Integer usuario;

    @Column(nullable = false)
    @Schema(description = "Información del videojuego", example = "1")
    private Integer videojuego;

    @Column(nullable = false)
    @Schema(description = "Cantidad de videojuegos", example = "1")
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de agregado", example = "2026-06-30T12:00:00")
    private LocalDateTime fechaAgregado;

    @Column(nullable = false)
    @Schema(description = "Precio unitario", example = "10.0")
    private Double precioUnitario;
}
