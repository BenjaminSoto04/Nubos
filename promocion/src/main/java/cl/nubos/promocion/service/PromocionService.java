package cl.nubos.promocion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.promocion.client.VideojuegoClient;
import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.repository.PromocionRepository;
import feign.FeignException;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<Promocion> listar() {
        return promocionRepository.findAll();
    }

    public Promocion buscarPorId(Integer id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));
    }

    public Promocion crear(Promocion promocion) {
        validarVideojuego(promocion.getVideojuegoId());
        return promocionRepository.save(promocion);
    }

    public Promocion actualizar(Integer id, Promocion promocionActualizada) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));

        validarVideojuego(promocionActualizada.getVideojuegoId());

        promocion.setVideojuegoId(promocionActualizada.getVideojuegoId());
        promocion.setPorcentajeDescuento(promocionActualizada.getPorcentajeDescuento());
        promocion.setFechaInicio(promocionActualizada.getFechaInicio());
        promocion.setFechaFin(promocionActualizada.getFechaFin());
        promocion.setCodigoPromocional(promocionActualizada.getCodigoPromocional());
        promocion.setActiva(promocionActualizada.getActiva());

        return promocionRepository.save(promocion);
    }

    public void eliminar(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada");
        }
        promocionRepository.deleteById(id);
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
