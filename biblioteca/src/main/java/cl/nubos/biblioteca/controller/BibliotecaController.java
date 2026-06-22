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

import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.service.BibliotecaService;

@RestController
@RequestMapping("/api/v1/biblioteca")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @GetMapping
    public ResponseEntity<List<Biblioteca>> listar() {
        List<Biblioteca> lista = bibliotecaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Biblioteca> crear(@RequestBody Biblioteca biblioteca) {
        Biblioteca nueva = bibliotecaService.crear(biblioteca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Biblioteca> actualizar(@PathVariable Integer id, @RequestBody Biblioteca biblioteca) {
        return ResponseEntity.ok(bibliotecaService.actualizar(id, biblioteca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        bibliotecaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
