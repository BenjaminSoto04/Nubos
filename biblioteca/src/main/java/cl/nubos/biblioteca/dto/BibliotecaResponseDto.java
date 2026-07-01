package cl.nubos.biblioteca.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BibliotecaResponseDto {
    private Integer id;
    private LocalDate fechaAdquisicion;
    private Integer horasJugadas;
    private LocalDateTime ultimaSesion;
    private UsuarioDto usuario;
    private VideojuegoDto videojuego;
}
