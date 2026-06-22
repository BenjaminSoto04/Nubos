package cl.nubos.videojuego.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.service.VideojuegoService;

@RestController
@RequestMapping("/api/v1/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<List<Videojuego>> listar() {
        List<Videojuego> lista = videojuegoService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(videojuegoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Videojuego> crear(@RequestBody Videojuego videojuego) {
        Videojuego nuevo = videojuegoService.crear(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> actualizar(@PathVariable Integer id, @RequestBody Videojuego videojuego) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuego));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
