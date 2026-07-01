package cl.nubos.resena.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResenaResponseDto {
    private Integer id;
    private Integer calificacion;
    private String comentario;
    private LocalDate fechaResena;
    private Boolean recomendado;
    private UsuarioDto usuario;
    private VideojuegoDto videojuego;
}
