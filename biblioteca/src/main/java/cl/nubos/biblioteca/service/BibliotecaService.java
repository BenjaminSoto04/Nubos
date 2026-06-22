package cl.nubos.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.biblioteca.client.UsuarioClient;
import cl.nubos.biblioteca.client.VideojuegoClient;
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

    public List<Biblioteca> listar() {
        return bibliotecaRepository.findAll();
    }

    public Biblioteca buscarPorId(Integer id) {
        return bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado"));
    }

    public Biblioteca crear(Biblioteca biblioteca) {
        validarUsuario(biblioteca.getUsuarioId());
        validarVideojuego(biblioteca.getVideojuegoId());
        return bibliotecaRepository.save(biblioteca);
    }

    public Biblioteca actualizar(Integer id, Biblioteca bibliotecaActualizada) {
        Biblioteca biblioteca = bibliotecaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado"));

        validarUsuario(bibliotecaActualizada.getUsuarioId());
        validarVideojuego(bibliotecaActualizada.getVideojuegoId());

        biblioteca.setUsuarioId(bibliotecaActualizada.getUsuarioId());
        biblioteca.setVideojuegoId(bibliotecaActualizada.getVideojuegoId());
        biblioteca.setFechaAdquisicion(bibliotecaActualizada.getFechaAdquisicion());
        biblioteca.setHorasJugadas(bibliotecaActualizada.getHorasJugadas());
        biblioteca.setUltimaSesion(bibliotecaActualizada.getUltimaSesion());

        return bibliotecaRepository.save(biblioteca);
    }

    public void eliminar(Integer id) {
        if (!bibliotecaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado");
        }
        bibliotecaRepository.deleteById(id);
    }

    private void validarUsuario(Integer usuarioId) {
        try {
            usuarioClient.obtenerPorId(usuarioId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Usuario con id " + usuarioId + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio usuario no disponible");
        }
    }

    private void validarVideojuego(Integer videojuegoId) {
        try {
            videojuegoClient.obtenerPorId(videojuegoId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Videojuego con id " + videojuegoId + " no existe");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Servicio videojuego no disponible");
        }
    }
}
