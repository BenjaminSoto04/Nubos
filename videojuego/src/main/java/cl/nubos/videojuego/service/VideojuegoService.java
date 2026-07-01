package cl.nubos.videojuego.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.videojuego.client.CategoriaClient;
import cl.nubos.videojuego.client.DesarrolladorClient;
import cl.nubos.videojuego.dto.VideojuegoResponseDto;
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

    public List<VideojuegoResponseDto> listar() {
        return videojuegoRepository.findAll().stream()
                .map(this::mapearAVideojuegoResponseDto)
                .toList();
    }

    public VideojuegoResponseDto buscarPorId(Integer id) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));
        return mapearAVideojuegoResponseDto(videojuego);
    }

    public VideojuegoResponseDto crear(Videojuego videojuego) {
        validarDesarrollador(videojuego.getDesarrollador());
        validarCategoria(videojuego.getCategoria());
        Videojuego guardado = videojuegoRepository.save(videojuego);
        return mapearAVideojuegoResponseDto(guardado);
    }

    public VideojuegoResponseDto actualizar(Integer id, Videojuego videojuegoActualizado) {
        Videojuego videojuego = videojuegoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));

        validarDesarrollador(videojuegoActualizado.getDesarrollador());
        validarCategoria(videojuegoActualizado.getCategoria());

        videojuego.setTitulo(videojuegoActualizado.getTitulo());
        videojuego.setDescripcion(videojuegoActualizado.getDescripcion());
        videojuego.setPrecio(videojuegoActualizado.getPrecio());
        videojuego.setFechaLanzamiento(videojuegoActualizado.getFechaLanzamiento());
        videojuego.setDesarrollador(videojuegoActualizado.getDesarrollador());
        videojuego.setCategoria(videojuegoActualizado.getCategoria());
        videojuego.setClasificacionEdad(videojuegoActualizado.getClasificacionEdad());

        Videojuego guardado = videojuegoRepository.save(videojuego);
        return mapearAVideojuegoResponseDto(guardado);
    }

    public void eliminar(Integer id) {
        if (!videojuegoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado");
        }
        videojuegoRepository.deleteById(id);
    }

    public VideojuegoResponseDto mapearAVideojuegoResponseDto(Videojuego videojuego) {
        VideojuegoResponseDto dto = new VideojuegoResponseDto();
        dto.setId(videojuego.getId());
        dto.setTitulo(videojuego.getTitulo());
        dto.setDescripcion(videojuego.getDescripcion());
        dto.setPrecio(videojuego.getPrecio());
        dto.setFechaLanzamiento(videojuego.getFechaLanzamiento());
        dto.setClasificacionEdad(videojuego.getClasificacionEdad());

        try {
            dto.setDesarrollador(desarrolladorClient.obtenerPorId(videojuego.getDesarrollador()));
        } catch (Exception e) {
            dto.setDesarrollador(null);
        }

        try {
            dto.setCategoria(categoriaClient.obtenerPorId(videojuego.getCategoria()));
        } catch (Exception e) {
            dto.setCategoria(null);
        }

        return dto;
    }

    private void validarDesarrollador(Integer desarrollador) {
        try {
            desarrolladorClient.obtenerPorId(desarrollador);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Desarrollador con id " + desarrollador + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio desarrollador no disponible");
        }
    }

    private void validarCategoria(Integer categoria) {
        try {
            categoriaClient.obtenerPorId(categoria);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Categoria con id " + categoria + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio categoria no disponible");
        }
    }
}
