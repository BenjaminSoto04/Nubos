package cl.nubos.promocion.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromocionResponseDto {
    private Integer id;
    private Integer porcentajeDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String codigoPromocional;
    private Boolean activa;
    private VideojuegoDto videojuego;
}
