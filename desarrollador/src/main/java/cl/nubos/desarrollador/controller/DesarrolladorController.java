package cl.nubos.desarrollador.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.nubos.desarrollador.model.Desarrollador;
import cl.nubos.desarrollador.service.DesarrolladorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/desarrolladores")
@Tag(name = "Desarrollador", description = "Operaciones con desarrolladores")
public class DesarrolladorController {

    @Autowired
    public DesarrolladorService desarrolladorService;

    @GetMapping
    @Operation(summary = "Obtener todos los desarrolladores", description = "Devuelve una lista de todos los desarrolladores")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Desarrollador>> getAllDesarrolladores() {
        List<Desarrollador> desarrolladores = desarrolladorService.getAllDesarrolladores();

        if (desarrolladores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(desarrolladores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un desarrollador por ID", description = "Devuelve un desarrollador según el ID proporcionado")
    public ResponseEntity<Desarrollador> obtenerPorId(@PathVariable Integer id) {
        try {
            Desarrollador desarrollador = desarrolladorService.getDesarrolladorById(id);
            if (desarrollador == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(desarrollador);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo desarrollador", description = "Agrega un nuevo desarrollador a la base de datos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Desarrollador> agregarDesarrollador(@RequestBody Desarrollador desarrollador) {
        Desarrollador nuevoDesarrollador = desarrolladorService.createDesarrollador(desarrollador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDesarrollador);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un desarrollador por ID", description = "Modifica un desarrollador según el ID proporcionado")
    public ResponseEntity<Desarrollador> actualizarDesarrollador(@PathVariable Integer id,
            @RequestBody Desarrollador desarrollador) {
        try {
            Desarrollador desarrolladorActualizado = desarrolladorService.updateDesarrollador(id, desarrollador);
            return ResponseEntity.ok(desarrolladorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un desarrollador por ID", description = "Elimina un desarrollador según el ID proporcionado")
    public ResponseEntity<Void> eliminarDesarrollador(@PathVariable Integer id) {
        try {
            desarrolladorService.deleteDesarrollador(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
