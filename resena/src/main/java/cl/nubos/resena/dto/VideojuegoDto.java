package cl.nubos.resena.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideojuegoDto {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Double precio;
    private LocalDate fechaLanzamiento;
    private String clasificacionEdad;
}
