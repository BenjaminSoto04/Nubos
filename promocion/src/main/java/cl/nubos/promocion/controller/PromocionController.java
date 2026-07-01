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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import cl.nubos.promocion.dto.PromocionResponseDto;
import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.service.PromocionService;

@RestController
@RequestMapping("/api/v1/promociones")
@Tag(name = "Promociones", description = "Operaciones con promociones")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @Operation(summary = "Obtener todas las promociones", description = "Devuelve una lista de todas las promociones")
    @GetMapping
    public ResponseEntity<List<PromocionResponseDto>> listar() {
        List<PromocionResponseDto> lista = promocionService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener una promoción por ID", description = "Devuelve una promoción según el ID proporcionado")
    @GetMapping("/{id}")
    public ResponseEntity<PromocionResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(promocionService.buscarPorId(id));
    }

    @Operation(summary = "Crear una nueva promoción", description = "Crea una nueva promoción")
    @PostMapping
    public ResponseEntity<PromocionResponseDto> crear(@RequestBody Promocion promocion) {
        PromocionResponseDto nueva = promocionService.crear(promocion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Actualizar una promoción por ID", description = "Actualiza una promoción según el ID proporcionado")
    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponseDto> actualizar(@PathVariable Integer id, @RequestBody Promocion promocion) {
        return ResponseEntity.ok(promocionService.actualizar(id, promocion));
    }

    @Operation(summary = "Eliminar una promoción por ID", description = "Elimina una promoción según el ID proporcionado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
