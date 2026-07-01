package cl.nubos.videojuego.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.nubos.videojuego.dto.DesarrolladorDto;

@FeignClient(name = "desarrollador")
public interface DesarrolladorClient {

    @GetMapping("/api/v1/desarrolladores/{id}")
    DesarrolladorDto obtenerPorId(@PathVariable("id") Integer id);
}
