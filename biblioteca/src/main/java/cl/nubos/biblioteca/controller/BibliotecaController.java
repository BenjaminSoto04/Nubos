package cl.nubos.biblioteca.controller;

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

import cl.nubos.biblioteca.dto.BibliotecaResponseDto;
import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.service.BibliotecaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/biblioteca")
@Tag(name = "Biblioteca", description = "API de biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping
    @Operation(summary = "Listar bibliotecas", description = "Devuelve una lista de todas las bibliotecas")
    public ResponseEntity<List<BibliotecaResponseDto>> listar() {
        List<BibliotecaResponseDto> lista = bibliotecaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una biblioteca por ID", description = "Devuelve una biblioteca según el ID proporcionado")
    public ResponseEntity<BibliotecaResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una biblioteca", description = "Crea una biblioteca")
    public ResponseEntity<BibliotecaResponseDto> crear(@RequestBody Biblioteca biblioteca) {
        BibliotecaResponseDto nueva = bibliotecaService.crear(biblioteca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una biblioteca por ID", description = "Actualiza una biblioteca según el ID proporcionado")
    public ResponseEntity<BibliotecaResponseDto> actualizar(@PathVariable Integer id,
            @RequestBody Biblioteca biblioteca) {
        return ResponseEntity.ok(bibliotecaService.actualizar(id, biblioteca));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una biblioteca por ID", description = "Elimina una biblioteca según el ID proporcionado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        bibliotecaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
