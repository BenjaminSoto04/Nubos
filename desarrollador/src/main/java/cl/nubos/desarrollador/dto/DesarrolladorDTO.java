package cl.nubos.desarrollador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesarrolladorDTO {

    private Integer id;
    private String nombre;
    private String estudio;
    private String correo;
    private String país;
}
