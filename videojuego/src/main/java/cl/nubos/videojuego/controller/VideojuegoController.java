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

import cl.nubos.videojuego.dto.VideojuegoResponseDto;
import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.service.VideojuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/videojuegos")
@Tag(name = "Videojuego", description = "Operaciones con videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    @Operation(summary = "Obtener todos los videojuegos", description = "Devuelve una lista de todos los videojuegos")
    public ResponseEntity<List<VideojuegoResponseDto>> listar() {
        List<VideojuegoResponseDto> lista = videojuegoService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un videojuego por ID", description = "Devuelve un videojuego según el ID proporcionado")
    public ResponseEntity<VideojuegoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(videojuegoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo videojuego", description = "Crea un nuevo videojuego")
    public ResponseEntity<VideojuegoResponseDto> crear(@RequestBody Videojuego videojuego) {
        VideojuegoResponseDto nuevo = videojuegoService.crear(videojuego);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un videojuego por ID", description = "Actualiza un videojuego según el ID proporcionado")
    public ResponseEntity<VideojuegoResponseDto> actualizar(@PathVariable Integer id,
            @RequestBody Videojuego videojuego) {
        return ResponseEntity.ok(videojuegoService.actualizar(id, videojuego));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un videojuego por ID", description = "Elimina un videojuego según el ID proporcionado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        videojuegoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
