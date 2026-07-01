package cl.nubos.resena.controller;

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

import cl.nubos.resena.dto.ResenaResponseDto;
import cl.nubos.resena.model.Resena;
import cl.nubos.resena.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/resenas")
@Tag(name = "Reseñas", description = "API de reseñas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    @Operation(summary = "Listar reseñas", description = "Devuelve una lista de todas las reseñas")
    public ResponseEntity<List<ResenaResponseDto>> listar() {
        List<ResenaResponseDto> lista = resenaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reseña por ID", description = "Devuelve una reseña según el ID proporcionado")
    public ResponseEntity<ResenaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una reseña", description = "Crea una reseña")
    public ResponseEntity<ResenaResponseDto> crear(@RequestBody Resena resena) {
        ResenaResponseDto nueva = resenaService.crear(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reseña por ID", description = "Actualiza una reseña según el ID proporcionado")
    public ResponseEntity<ResenaResponseDto> actualizar(@PathVariable Integer id, @RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.actualizar(id, resena));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reseña por ID", description = "Elimina una reseña según el ID proporcionado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
