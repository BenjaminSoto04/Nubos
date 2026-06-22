package cl.nubos.resena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.resena.client.UsuarioClient;
import cl.nubos.resena.client.VideojuegoClient;
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

    public List<Resena> listar() {
        return resenaRepository.findAll();
    }

    public Resena buscarPorId(Integer id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));
    }

    public Resena crear(Resena resena) {
        validarUsuario(resena.getUsuarioId());
        validarVideojuego(resena.getVideojuegoId());
        return resenaRepository.save(resena);
    }

    public Resena actualizar(Integer id, Resena resenaActualizada) {
        Resena resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));

        validarUsuario(resenaActualizada.getUsuarioId());
        validarVideojuego(resenaActualizada.getVideojuegoId());

        resena.setUsuarioId(resenaActualizada.getUsuarioId());
        resena.setVideojuegoId(resenaActualizada.getVideojuegoId());
        resena.setCalificacion(resenaActualizada.getCalificacion());
        resena.setComentario(resenaActualizada.getComentario());
        resena.setFechaResena(resenaActualizada.getFechaResena());
        resena.setRecomendado(resenaActualizada.getRecomendado());

        return resenaRepository.save(resena);
    }

    public void eliminar(Integer id) {
        if (!resenaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada");
        }
        resenaRepository.deleteById(id);
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
