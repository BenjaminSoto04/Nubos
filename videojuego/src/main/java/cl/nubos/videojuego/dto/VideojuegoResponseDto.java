package cl.nubos.videojuego.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoResponseDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private LocalDate fechaLanzamiento;
    private String clasificacionEdad;
    private DesarrolladorDto desarrollador;
    private CategoriaDto categoria;
}
