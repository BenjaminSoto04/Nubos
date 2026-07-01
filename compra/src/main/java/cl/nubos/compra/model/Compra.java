package cl.nubos.compra.model;

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
@Table(name = "compra")
@Schema(description = "Modelo de compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id único de la compra", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Id único del usuario", example = "1")
    private Integer usuario;

    @Column(nullable = false)
    @Schema(description = "Id único del videojuego", example = "1")
    private Integer videojuego;

    @Column(nullable = false)
    @Schema(description = "Fecha de la compra", example = "2022-01-01T00:00:00")
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    @Schema(description = "Monto total de la compra", example = "14990.0")
    private Double montoTotal;

    @Column(nullable = false)
    @Schema(description = "Método de pago", example = "Tarjeta de Crédito")
    private String metodoPago;

    @Column(nullable = false)
    @Schema(description = "Estado de la compra", example = "Procesada")
    private String estado;
}
