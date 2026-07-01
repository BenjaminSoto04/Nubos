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

import cl.nubos.carrito.dto.CarritoResponseDto;
import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/carrito")
@Tag(name = "Carrito", description = "Operaciones con el carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    @Operation(summary = "Listar todos los carritos", description = "Devuelve una lista de todos los carritos")
    public ResponseEntity<List<CarritoResponseDto>> listar() {
        List<CarritoResponseDto> lista = carritoService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un carrito por ID", description = "Devuelve un carrito según el ID proporcionado")
    public ResponseEntity<CarritoResponseDto> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(carritoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo carrito", description = "Agrega un nuevo carrito a la base de datos")
    public ResponseEntity<CarritoResponseDto> crear(@RequestBody Carrito carrito) {
        CarritoResponseDto nuevo = carritoService.crear(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un carrito por ID", description = "Actualiza un carrito según el ID proporcionado")
    public ResponseEntity<CarritoResponseDto> actualizar(@PathVariable Integer id, @RequestBody Carrito carrito) {
        return ResponseEntity.ok(carritoService.actualizar(id, carrito));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un carrito por ID", description = "Elimina un carrito según el ID proporcionado")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        carritoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
