package cl.nubos.biblioteca.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.biblioteca.dto.VideojuegoDto;

@FeignClient(name = "videojuego")
public interface VideojuegoClient {

    @GetMapping("/api/v1/videojuegos/{id}")
    VideojuegoDto obtenerPorId(@PathVariable("id") Integer id);
}
