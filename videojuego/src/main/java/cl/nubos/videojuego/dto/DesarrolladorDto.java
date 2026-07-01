package cl.nubos.videojuego.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesarrolladorDto {
    private Integer id;
    private String nombre;
    private String estudio;
    private String correo;
}
