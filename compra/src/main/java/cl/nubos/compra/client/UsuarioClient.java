package cl.nubos.compra.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.compra.dto.UsuarioDto;

@FeignClient(name = "usuario", url = "http://localhost:8082")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDto obtenerPorId(@PathVariable("id") Integer id);
}
