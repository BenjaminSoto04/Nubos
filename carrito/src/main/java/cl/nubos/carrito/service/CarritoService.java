package cl.nubos.carrito.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.carrito.client.UsuarioClient;
import cl.nubos.carrito.client.VideojuegoClient;
import cl.nubos.carrito.dto.CarritoResponseDto;
import cl.nubos.carrito.dto.UsuarioDto;
import cl.nubos.carrito.dto.VideojuegoDto;
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

    public List<CarritoResponseDto> listar() {
        return carritoRepository.findAll().stream()
                .map(this::mapearACarritoResponseDto)
                .toList();
    }

    public CarritoResponseDto buscarPorId(Integer id) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));
        return mapearACarritoResponseDto(carrito);
    }

    public CarritoResponseDto crear(Carrito carrito) {
        validarUsuario(carrito.getUsuario());
        validarVideojuego(carrito.getVideojuego());
        Carrito guardado = carritoRepository.save(carrito);
        return mapearACarritoResponseDto(guardado);
    }

    public CarritoResponseDto actualizar(Integer id, Carrito carritoActualizado) {
        Carrito carrito = carritoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));

        validarUsuario(carritoActualizado.getUsuario());
        validarVideojuego(carritoActualizado.getVideojuego());

        carrito.setUsuario(carritoActualizado.getUsuario());
        carrito.setVideojuego(carritoActualizado.getVideojuego());
        carrito.setCantidad(carritoActualizado.getCantidad());
        carrito.setFechaAgregado(carritoActualizado.getFechaAgregado());
        carrito.setPrecioUnitario(carritoActualizado.getPrecioUnitario());

        Carrito guardado = carritoRepository.save(carrito);
        return mapearACarritoResponseDto(guardado);
    }

    public void eliminar(Integer id) {
        if (!carritoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado");
        }
        carritoRepository.deleteById(id);
    }

    public CarritoResponseDto mapearACarritoResponseDto(Carrito carrito) {
        CarritoResponseDto dto = new CarritoResponseDto();
        dto.setId(carrito.getId());
        dto.setCantidad(carrito.getCantidad());
        dto.setFechaAgregado(carrito.getFechaAgregado());
        dto.setPrecioUnitario(carrito.getPrecioUnitario());

        try {
            dto.setUsuario(usuarioClient.obtenerPorId(carrito.getUsuario()));
        } catch (Exception e) {
            dto.setUsuario(null);
        }

        try {
            dto.setVideojuego(videojuegoClient.obtenerPorId(carrito.getVideojuego()));
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
