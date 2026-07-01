package cl.nubos.compra.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponseDto {
    private Integer id;
    private LocalDateTime fechaCompra;
    private Double montoTotal;
    private String metodoPago;
    private String estado;
    private UsuarioDto usuario;
    private VideojuegoDto videojuego;
}
