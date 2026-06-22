package cl.nubos.compra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.compra.client.UsuarioClient;
import cl.nubos.compra.client.VideojuegoClient;
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

    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Integer id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));
    }

    public Compra crear(Compra compra) {
        validarUsuario(compra.getUsuarioId());
        validarVideojuego(compra.getVideojuegoId());
        return compraRepository.save(compra);
    }

    public Compra actualizar(Integer id, Compra compraActualizada) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));

        validarUsuario(compraActualizada.getUsuarioId());
        validarVideojuego(compraActualizada.getVideojuegoId());

        compra.setUsuarioId(compraActualizada.getUsuarioId());
        compra.setVideojuegoId(compraActualizada.getVideojuegoId());
        compra.setFechaCompra(compraActualizada.getFechaCompra());
        compra.setMontoTotal(compraActualizada.getMontoTotal());
        compra.setMetodoPago(compraActualizada.getMetodoPago());
        compra.setEstado(compraActualizada.getEstado());

        return compraRepository.save(compra);
    }

    public void eliminar(Integer id) {
        if (!compraRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada");
        }
        compraRepository.deleteById(id);
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
