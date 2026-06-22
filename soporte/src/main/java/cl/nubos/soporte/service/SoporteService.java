package cl.nubos.soporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.nubos.soporte.model.Soporte;
import cl.nubos.soporte.repository.SoporteRepository;

@Service
public class SoporteService {

    @Autowired
    private SoporteRepository soporteRepository;

    public List<Soporte> getAllReportes() {
        return soporteRepository.findAll();
    }

    public Soporte getReporteById(Integer id) {
        return soporteRepository.findById(id).orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    public Soporte crearReporte(Soporte soporte) {
        return soporteRepository.save(soporte);
    }

    public Soporte actualizarReporte(Integer id, Soporte reporteActualizado) {
        Soporte soporte = soporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        soporte.setTitulo(reporteActualizado.getTitulo());
        soporte.setDescripcion(reporteActualizado.getDescripcion());
        soporte.setFechaReporte(reporteActualizado.getFechaReporte());
        soporte.setEstado(reporteActualizado.getEstado());

        return soporteRepository.save(soporte);
    }

    public void eliminarReporte(Integer id) {
        if (!soporteRepository.existsById(id)) {
            throw new RuntimeException("Reporte no encontrado");
        }
        soporteRepository.deleteById(id);
    }
}
