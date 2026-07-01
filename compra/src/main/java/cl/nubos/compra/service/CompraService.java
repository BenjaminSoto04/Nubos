package cl.nubos.compra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.compra.client.UsuarioClient;
import cl.nubos.compra.client.VideojuegoClient;
import cl.nubos.compra.dto.CompraResponseDto;
import cl.nubos.compra.model.Compra;
import cl.nubos.compra.repository.CompraRepository;
import feign.FeignException;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<CompraResponseDto> listar() {
        return compraRepository.findAll().stream()
                .map(this::mapearACompraResponseDto)
                .toList();
    }

    public CompraResponseDto buscarPorId(Integer id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));
        return mapearACompraResponseDto(compra);
    }

    public CompraResponseDto crear(Compra compra) {
        validarUsuario(compra.getUsuario());
        validarVideojuego(compra.getVideojuego());
        Compra guardada = compraRepository.save(compra);
        return mapearACompraResponseDto(guardada);
    }

    public CompraResponseDto actualizar(Integer id, Compra compraActualizada) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));

        validarUsuario(compraActualizada.getUsuario());
        validarVideojuego(compraActualizada.getVideojuego());

        compra.setUsuario(compraActualizada.getUsuario());
        compra.setVideojuego(compraActualizada.getVideojuego());
        compra.setFechaCompra(compraActualizada.getFechaCompra());
        compra.setMontoTotal(compraActualizada.getMontoTotal());
        compra.setMetodoPago(compraActualizada.getMetodoPago());
        compra.setEstado(compraActualizada.getEstado());

        Compra guardada = compraRepository.save(compra);
        return mapearACompraResponseDto(guardada);
    }

    public void eliminar(Integer id) {
        if (!compraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada");
        }
        compraRepository.deleteById(id);
    }

    public CompraResponseDto mapearACompraResponseDto(Compra compra) {
        CompraResponseDto dto = new CompraResponseDto();
        dto.setId(compra.getId());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setMontoTotal(compra.getMontoTotal());
        dto.setMetodoPago(compra.getMetodoPago());
        dto.setEstado(compra.getEstado());

        try {
            dto.setUsuario(usuarioClient.obtenerPorId(compra.getUsuario()));
        } catch (Exception e) {
            dto.setUsuario(null);
        }

        try {
            dto.setVideojuego(videojuegoClient.obtenerPorId(compra.getVideojuego()));
        } catch (Exception e) {
            dto.setVideojuego(null);
        }

        return dto;
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
