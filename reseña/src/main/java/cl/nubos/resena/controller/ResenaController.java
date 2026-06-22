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

import cl.nubos.resena.model.Resena;
import cl.nubos.resena.service.ResenaService;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public ResponseEntity<List<Resena>> listar() {
        List<Resena> lista = resenaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(resenaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Resena> crear(@RequestBody Resena resena) {
        Resena nueva = resenaService.crear(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizar(@PathVariable Integer id, @RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.actualizar(id, resena));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
