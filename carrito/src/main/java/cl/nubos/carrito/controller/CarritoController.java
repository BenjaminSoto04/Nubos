package cl.nubos.carrito.controller;

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

import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.service.CarritoService;

@RestController
@RequestMapping("/api/v1/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<Carrito>> listar() {
        List<Carrito> lista = carritoService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(carritoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Carrito> crear(@RequestBody Carrito carrito) {
        Carrito nuevo = carritoService.crear(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrito> actualizar(@PathVariable Integer id, @RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.actualizar(id, carrito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
