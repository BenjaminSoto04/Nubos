package cl.nubos.carrito.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponseDto {
    private Integer id;
    private Integer cantidad;
    private LocalDateTime fechaAgregado;
    private Double precioUnitario;
    private UsuarioDto usuario;
    private VideojuegoDto videojuego;
}
