package cl.nubos.desarrollador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.nubos.desarrollador.model.Desarrollador;
import cl.nubos.desarrollador.repository.DesarrolladorRepository;

@Service
public class DesarrolladorService {

    @Autowired
    private DesarrolladorRepository desarrolladorRepository;

    public List<Desarrollador> getAllDesarrolladores() {
        return desarrolladorRepository.findAll();
    }

    public Desarrollador getDesarrolladorById(Integer id) {
        return desarrolladorRepository.findById(id).orElseThrow(() -> new RuntimeException("Desarrollador no encontrado"));
    }

    public Desarrollador createDesarrollador(Desarrollador desarrollador) {
        return desarrolladorRepository.save(desarrollador);
    }

    public Desarrollador updateDesarrollador(Integer id, Desarrollador desarrolladorActualizado) {
        Desarrollador desarrollador = desarrolladorRepository.findById(id).orElseThrow(() -> new RuntimeException("Desarrolador no necontrado"));
        desarrollador.setNombre(desarrolladorActualizado.getNombre());
        desarrollador.setEstudio(desarrolladorActualizado.getEstudio());
        desarrollador.setCorreo(desarrolladorActualizado.getCorreo());
        desarrollador.setPaís(desarrolladorActualizado.getPaís());
        desarrollador.setContraseña(desarrolladorActualizado.getContraseña());

        return desarrolladorRepository.save(desarrollador);
    }

    public void deleteDesarrollador(Integer id) {
        if (!desarrolladorRepository.existsById(id)) {
            throw new RuntimeException("Desarrollador no encontrado");
        }
        desarrolladorRepository.deleteById(id);
    }
}
