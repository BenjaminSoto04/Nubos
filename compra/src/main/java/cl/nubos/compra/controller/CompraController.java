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

import cl.nubos.compra.dto.CompraResponseDto;
import cl.nubos.compra.model.Compra;
import cl.nubos.compra.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/compras")
@Tag(name = "Compra", description = "Operaciones con compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    @Operation(summary = "Obtener todas las compras", description = "Devuelve una lista de todas las compras")
    public ResponseEntity<List<CompraResponseDto>> listar() {
        List<CompraResponseDto> lista = compraService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una compra por ID", description = "Devuelve una compra según el ID proporcionado")
    public ResponseEntity<CompraResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva compra", description = "Crea una nueva compra")
    public ResponseEntity<CompraResponseDto> crear(@RequestBody Compra compra) {
        CompraResponseDto nueva = compraService.crear(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una compra por ID", description = "Actualiza una compra según el ID proporcionado")
    public ResponseEntity<CompraResponseDto> actualizar(@PathVariable Integer id, @RequestBody Compra compra) {
        return ResponseEntity.ok(compraService.actualizar(id, compra));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una compra por ID", description = "Elimina una compra según el ID proporcionado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        compraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
