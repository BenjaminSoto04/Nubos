package cl.nubos.resena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.resena.client.UsuarioClient;
import cl.nubos.resena.client.VideojuegoClient;
import cl.nubos.resena.dto.ResenaResponseDto;
import cl.nubos.resena.dto.UsuarioDto;
import cl.nubos.resena.dto.VideojuegoDto;
import cl.nubos.resena.model.Resena;
import cl.nubos.resena.repository.ResenaRepository;
import feign.FeignException;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<ResenaResponseDto> listar() {
        return resenaRepository.findAll().stream()
                .map(this::mapearAResenaResponseDto)
                .toList();
    }

    public ResenaResponseDto buscarPorId(Integer id) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));
        return mapearAResenaResponseDto(resena);
    }

    public ResenaResponseDto crear(Resena resena) {
        validarUsuario(resena.getUsuario());
        validarVideojuego(resena.getVideojuego());
        Resena guardada = resenaRepository.save(resena);
        return mapearAResenaResponseDto(guardada);
    }

    public ResenaResponseDto actualizar(Integer id, Resena resenaActualizada) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));

        validarUsuario(resenaActualizada.getUsuario());
        validarVideojuego(resenaActualizada.getVideojuego());

        resena.setUsuario(resenaActualizada.getUsuario());
        resena.setVideojuego(resenaActualizada.getVideojuego());
        resena.setCalificacion(resenaActualizada.getCalificacion());
        resena.setComentario(resenaActualizada.getComentario());
        resena.setFechaResena(resenaActualizada.getFechaResena());
        resena.setRecomendado(resenaActualizada.getRecomendado());

        Resena guardada = resenaRepository.save(resena);
        return mapearAResenaResponseDto(guardada);
    }

    public void eliminar(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada");
        }
        resenaRepository.deleteById(id);
    }

    public ResenaResponseDto mapearAResenaResponseDto(Resena resena) {
        ResenaResponseDto dto = new ResenaResponseDto();
        dto.setId(resena.getId());
        dto.setCalificacion(resena.getCalificacion());
        dto.setComentario(resena.getComentario());
        dto.setFechaResena(resena.getFechaResena());
        dto.setRecomendado(resena.getRecomendado());

        try {
            dto.setUsuario(usuarioClient.obtenerPorId(resena.getUsuario()));
        } catch (Exception e) {
            dto.setUsuario(null);
        }

        try {
            dto.setVideojuego(videojuegoClient.obtenerPorId(resena.getVideojuego()));
        } catch (Exception e) {
            dto.setVideojuego(null);
        }

        return dto;
    }

    private void validarUsuario(Integer usuario) {
        try {
            usuarioClient.obtenerPorId(usuario);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Usuario con id " + usuario + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio usuario no disponible");
        }
    }

    private void validarVideojuego(Integer videojuego) {
        try {
            videojuegoClient.obtenerPorId(videojuego);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Videojuego con id " + videojuego + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio videojuego no disponible");
        }
    }
}
