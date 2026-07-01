package cl.nubos.biblioteca.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.biblioteca.dto.UsuarioDto;

@FeignClient(name = "usuario")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDto obtenerPorId(@PathVariable("id") Integer id);
}
