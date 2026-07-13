package cl.nubos.videojuego.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoDTO {
    private Integer id;
    private String titulo;
    private Double precio;
}
