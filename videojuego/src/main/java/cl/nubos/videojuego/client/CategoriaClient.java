package cl.nubos.videojuego.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.videojuego.dto.CategoriaDto;

@FeignClient(name = "categoria", url = "http://localhost:8084")
public interface CategoriaClient {

    @GetMapping("/api/v1/categorias/{id}")
    CategoriaDto obtenerPorId(@PathVariable("id") Integer id);
}
