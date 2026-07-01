package cl.nubos.promocion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.promocion.client.VideojuegoClient;
import cl.nubos.promocion.dto.PromocionResponseDto;
import cl.nubos.promocion.dto.VideojuegoDto;
import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.repository.PromocionRepository;
import feign.FeignException;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private VideojuegoClient videojuegoClient;

    public List<PromocionResponseDto> listar() {
        return promocionRepository.findAll().stream()
                .map(this::mapearAPromocionResponseDto)
                .toList();
    }

    public PromocionResponseDto buscarPorId(Integer id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));
        return mapearAPromocionResponseDto(promocion);
    }

    public PromocionResponseDto crear(Promocion promocion) {
        validarVideojuego(promocion.getVideojuego());
        Promocion guardada = promocionRepository.save(promocion);
        return mapearAPromocionResponseDto(guardada);
    }

    public PromocionResponseDto actualizar(Integer id, Promocion promocionActualizada) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));

        validarVideojuego(promocionActualizada.getVideojuego());

        promocion.setVideojuego(promocionActualizada.getVideojuego());
        promocion.setPorcentajeDescuento(promocionActualizada.getPorcentajeDescuento());
        promocion.setFechaInicio(promocionActualizada.getFechaInicio());
        promocion.setFechaFin(promocionActualizada.getFechaFin());
        promocion.setCodigoPromocional(promocionActualizada.getCodigoPromocional());
        promocion.setActiva(promocionActualizada.getActiva());

        Promocion guardada = promocionRepository.save(promocion);
        return mapearAPromocionResponseDto(guardada);
    }

    public void eliminar(Integer id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada");
        }
        promocionRepository.deleteById(id);
    }

    public PromocionResponseDto mapearAPromocionResponseDto(Promocion promocion) {
        PromocionResponseDto dto = new PromocionResponseDto();
        dto.setId(promocion.getId());
        dto.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
        dto.setFechaInicio(promocion.getFechaInicio());
        dto.setFechaFin(promocion.getFechaFin());
        dto.setCodigoPromocional(promocion.getCodigoPromocional());
        dto.setActiva(promocion.getActiva());

        try {
            dto.setVideojuego(videojuegoClient.obtenerPorId(promocion.getVideojuego()));
        } catch (Exception e) {
            dto.setVideojuego(null);
        }

        return dto;
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
