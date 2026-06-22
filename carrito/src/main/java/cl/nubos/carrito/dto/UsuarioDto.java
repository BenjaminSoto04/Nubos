package cl.nubos.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private Integer edad;
    private String correo;
}
