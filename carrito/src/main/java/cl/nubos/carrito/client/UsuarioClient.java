package cl.nubos.carrito.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.nubos.carrito.dto.UsuarioDto;

@FeignClient(name = "usuario")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDto obtenerPorId(@PathVariable("id") Integer id);
}
