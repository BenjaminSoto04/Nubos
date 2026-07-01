package cl.nubos.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.biblioteca.client.UsuarioClient;
import cl.nubos.biblioteca.client.VideojuegoClient;
import cl.nubos.biblioteca.dto.BibliotecaResponseDto;
import cl.nubos.biblioteca.dto.UsuarioDto;
import cl.nubos.biblioteca.dto.VideojuegoDto;
import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.repository.BibliotecaRepository;
import feign.FeignException;

@Service
public class BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<BibliotecaResponseDto> listar() {
        return bibliotecaRepository.findAll().stream()
                .map(this::mapearABibliotecaResponseDto)
                .toList();
    }

    public BibliotecaResponseDto buscarPorId(Integer id) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registro de Biblioteca no encontrado"));
        return mapearABibliotecaResponseDto(biblioteca);
    }

    public BibliotecaResponseDto crear(Biblioteca biblioteca) {
        validarUsuario(biblioteca.getUsuario());
        validarVideojuego(biblioteca.getVideojuego());
        Biblioteca guardado = bibliotecaRepository.save(biblioteca);
        return mapearABibliotecaResponseDto(guardado);
    }

    public BibliotecaResponseDto actualizar(Integer id, Biblioteca bibliotecaActualizada) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registro de Biblioteca no encontrado"));

        validarUsuario(bibliotecaActualizada.getUsuario());
        validarVideojuego(bibliotecaActualizada.getVideojuego());

        biblioteca.setUsuario(bibliotecaActualizada.getUsuario());
        biblioteca.setVideojuego(bibliotecaActualizada.getVideojuego());
        biblioteca.setFechaAdquisicion(bibliotecaActualizada.getFechaAdquisicion());
        biblioteca.setHorasJugadas(bibliotecaActualizada.getHorasJugadas());
        biblioteca.setUltimaSesion(bibliotecaActualizada.getUltimaSesion());

        Biblioteca guardado = bibliotecaRepository.save(biblioteca);
        return mapearABibliotecaResponseDto(guardado);
    }

    public void eliminar(Integer id) {
        if (!bibliotecaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado");
        }
        bibliotecaRepository.deleteById(id);
    }

    public BibliotecaResponseDto mapearABibliotecaResponseDto(Biblioteca biblioteca) {
        BibliotecaResponseDto dto = new BibliotecaResponseDto();
        dto.setId(biblioteca.getId());
        dto.setFechaAdquisicion(biblioteca.getFechaAdquisicion());
        dto.setHorasJugadas(biblioteca.getHorasJugadas());
        dto.setUltimaSesion(biblioteca.getUltimaSesion());

        try {
            dto.setUsuario(usuarioClient.obtenerPorId(biblioteca.getUsuario()));
        } catch (Exception e) {
            dto.setUsuario(null);
        }

        try {
            dto.setVideojuego(videojuegoClient.obtenerPorId(biblioteca.getVideojuego()));
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
