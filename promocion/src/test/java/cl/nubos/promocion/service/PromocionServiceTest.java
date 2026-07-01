package cl.nubos.promocion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.promocion.client.VideojuegoClient;
import cl.nubos.promocion.dto.PromocionResponseDto;
import cl.nubos.promocion.dto.VideojuegoDto;
import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.repository.PromocionRepository;

@ExtendWith(MockitoExtension.class)
public class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @Mock
    private VideojuegoClient videojuegoClient;

    @InjectMocks
    private PromocionService promocionService;

    private Promocion promocion;
    private VideojuegoDto videojuegoDto;

    @BeforeEach
    public void setup() {
        promocion = new Promocion();
        promocion.setId(1);
        promocion.setVideojuego(100);
        promocion.setPorcentajeDescuento(20);
        promocion.setFechaInicio(LocalDate.of(2026, 6, 30));
        promocion.setFechaFin(LocalDate.of(2026, 7, 30));
        promocion.setCodigoPromocional("SUMMER26");
        promocion.setActiva(true);

        videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
    }

    @Test
    void buscarPromocionPorIdExistente() {
        when(promocionRepository.findById(1)).thenReturn(Optional.of(promocion));
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);

        PromocionResponseDto dto = promocionService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals(20, dto.getPorcentajeDescuento());
        assertEquals("SUMMER26", dto.getCodigoPromocional());
        assertEquals(100, dto.getVideojuego().getId());
    }

    @Test
    void buscarPromocionPorIdNoExistente() {
        when(promocionRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            promocionService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Promocion no encontrada", exception.getReason());
    }

    @Test
    void crearPromocion() {
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);
        when(promocionRepository.save(promocion)).thenReturn(promocion);

        PromocionResponseDto dto = promocionService.crear(promocion);

        assertEquals(1, dto.getId());
        verify(promocionRepository, times(1)).save(promocion);
    }

    @Test
    void eliminarPromocionExistente() {
        when(promocionRepository.existsById(1)).thenReturn(true);
        promocionService.eliminar(1);

        verify(promocionRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPromocionNoExistente() {
        when(promocionRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            promocionService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Promocion no encontrada", exception.getReason());
        verify(promocionRepository, never()).deleteById(7);
    }
}
