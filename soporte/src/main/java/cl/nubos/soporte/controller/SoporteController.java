package cl.nubos.soporte.controller;

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

import cl.nubos.soporte.model.Soporte;
import cl.nubos.soporte.service.SoporteService;

@RestController
@RequestMapping("/api/v1/soporte")
public class SoporteController {

    @Autowired
    private SoporteService soporteService;

    @GetMapping
    public ResponseEntity<List<Soporte>> listarReportes() {
        List<Soporte> reportes = soporteService.getAllReportes();

        if (reportes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Soporte> obtenerPorId(@PathVariable Integer id) {
        try {
            Soporte reporte = soporteService.getReporteById(id);
            return ResponseEntity.ok(reporte);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Soporte> crearReporte(@RequestBody Soporte soporte) {
        Soporte nuevoReporte = soporteService.crearReporte(soporte);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoReporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Soporte> actualizarReporte(@PathVariable Integer id, @RequestBody Soporte soporte) {
        try {
            Soporte reporteActualizado = soporteService.actualizarReporte(id, soporte);
            return ResponseEntity.ok(reporteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Integer id) {
        try {
            soporteService.eliminarReporte(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
