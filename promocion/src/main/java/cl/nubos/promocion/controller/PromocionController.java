package cl.nubos.promocion.controller;

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

import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.service.PromocionService;

@RestController
@RequestMapping("/api/v1/promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping
    public ResponseEntity<List<Promocion>> listar() {
        List<Promocion> lista = promocionService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocion> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(promocionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Promocion> crear(@RequestBody Promocion promocion) {
        Promocion nueva = promocionService.crear(promocion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promocion> actualizar(@PathVariable Integer id, @RequestBody Promocion promocion) {
        return ResponseEntity.ok(promocionService.actualizar(id, promocion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
