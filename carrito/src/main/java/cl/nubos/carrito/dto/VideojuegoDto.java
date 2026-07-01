package cl.nubos.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoDto {
    private Integer id;
    private String titulo;
    private Double precio;
}
