package cl.nubos.compra.controller;

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

import cl.nubos.compra.model.Compra;
import cl.nubos.compra.service.CompraService;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        List<Compra> lista = compraService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Compra> crear(@RequestBody Compra compra) {
        Compra nueva = compraService.crear(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> actualizar(@PathVariable Integer id, @RequestBody Compra compra) {
        return ResponseEntity.ok(compraService.actualizar(id, compra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        compraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
