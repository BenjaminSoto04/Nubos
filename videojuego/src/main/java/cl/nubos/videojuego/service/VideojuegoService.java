package cl.nubos.videojuego.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.videojuego.client.CategoriaClient;
import cl.nubos.videojuego.client.DesarrolladorClient;
import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.repository.VideojuegoRepository;
import feign.FeignException;

@Service
public class VideojuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @Autowired
    private DesarrolladorClient desarrolladorClient;

    @Autowired
    private CategoriaClient categoriaClient;

    public List<Videojuego> listar() {
        return videojuegoRepository.findAll();
    }

    public Videojuego buscarPorId(Integer id) {
        return videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));
    }

    public Videojuego crear(Videojuego videojuego) {
        validarDesarrollador(videojuego.getDesarrolladorId());
        validarCategoria(videojuego.getCategoriaId());
        return videojuegoRepository.save(videojuego);
    }

    public Videojuego actualizar(Integer id, Videojuego videojuegoActualizado) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));

        validarDesarrollador(videojuegoActualizado.getDesarrolladorId());
        validarCategoria(videojuegoActualizado.getCategoriaId());

        videojuego.setTitulo(videojuegoActualizado.getTitulo());
        videojuego.setDescripcion(videojuegoActualizado.getDescripcion());
        videojuego.setPrecio(videojuegoActualizado.getPrecio());
        videojuego.setFechaLanzamiento(videojuegoActualizado.getFechaLanzamiento());
        videojuego.setDesarrolladorId(videojuegoActualizado.getDesarrolladorId());
        videojuego.setCategoriaId(videojuegoActualizado.getCategoriaId());
        videojuego.setClasificacionEdad(videojuegoActualizado.getClasificacionEdad());

        return videojuegoRepository.save(videojuego);
    }

    public void eliminar(Integer id) {
        if (!videojuegoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado");
        }
        videojuegoRepository.deleteById(id);
    }

    private void validarDesarrollador(Integer desarrolladorId) {
        try {
            desarrolladorClient.obtenerPorId(desarrolladorId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Desarrollador con id " + desarrolladorId + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio desarrollador no disponible");
        }
    }

    private void validarCategoria(Integer categoriaId) {
        try {
            categoriaClient.obtenerPorId(categoriaId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Categoria con id " + categoriaId + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio categoria no disponible");
        }
    }
}
