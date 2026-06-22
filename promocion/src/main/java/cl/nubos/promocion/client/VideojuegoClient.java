package cl.nubos.promocion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.promocion.dto.VideojuegoDto;

@FeignClient(name = "videojuego", url = "http://localhost:8083")
public interface VideojuegoClient {

    @GetMapping("/api/v1/videojuegos/{id}")
    VideojuegoDto obtenerPorId(@PathVariable("id") Integer id);
}
