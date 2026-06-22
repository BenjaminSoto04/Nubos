package cl.nubos.carrito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.carrito.client.UsuarioClient;
import cl.nubos.carrito.client.VideojuegoClient;
import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.repository.CarritoRepository;
import feign.FeignException;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<Carrito> listar() {
        return carritoRepository.findAll();
    }

    public Carrito buscarPorId(Integer id) {
        return carritoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));
    }

    public Carrito crear(Carrito carrito) {
        validarUsuario(carrito.getUsuarioId());
        validarVideojuego(carrito.getVideojuegoId());
        return carritoRepository.save(carrito);
    }

    public Carrito actualizar(Integer id, Carrito carritoActualizado) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));

        validarUsuario(carritoActualizado.getUsuarioId());
        validarVideojuego(carritoActualizado.getVideojuegoId());

        carrito.setUsuarioId(carritoActualizado.getUsuarioId());
        carrito.setVideojuegoId(carritoActualizado.getVideojuegoId());
        carrito.setCantidad(carritoActualizado.getCantidad());
        carrito.setFechaAgregado(carritoActualizado.getFechaAgregado());
        carrito.setPrecioUnitario(carritoActualizado.getPrecioUnitario());

        return carritoRepository.save(carrito);
    }

    public void eliminar(Integer id) {
        if (!carritoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
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
