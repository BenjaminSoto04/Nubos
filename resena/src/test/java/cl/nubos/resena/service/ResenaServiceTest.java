package cl.nubos.resena.service;

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

import cl.nubos.resena.client.UsuarioClient;
import cl.nubos.resena.client.VideojuegoClient;
import cl.nubos.resena.dto.ResenaResponseDto;
import cl.nubos.resena.dto.UsuarioDto;
import cl.nubos.resena.dto.VideojuegoDto;
import cl.nubos.resena.model.Resena;
import cl.nubos.resena.repository.ResenaRepository;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private VideojuegoClient videojuegoClient;

    @InjectMocks
    private ResenaService resenaService;

    private Resena resena;
    private UsuarioDto usuarioDto;
    private VideojuegoDto videojuegoDto;

    @BeforeEach
    public void setup() {
        resena = new Resena();
        resena.setId(1);
        resena.setUsuario(10);
        resena.setVideojuego(100);
        resena.setCalificacion(5);
        resena.setComentario("Excelente juego");
        resena.setFechaResena(LocalDate.of(2026, 6, 30));
        resena.setRecomendado(true);

        usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        videojuegoDto = new VideojuegoDto(100, "Hollow Knight", "Metroidvania", 15.0, LocalDate.of(2017, 2, 24), "Everyone");
    }

    @Test
    void buscarResenaPorIdExistente() {
        when(resenaRepository.findById(1)).thenReturn(Optional.of(resena));
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);

        ResenaResponseDto dto = resenaService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals(5, dto.getCalificacion());
        assertEquals("Excelente juego", dto.getComentario());
        assertEquals(10, dto.getUsuario().getId());
        assertEquals(100, dto.getVideojuego().getId());
    }

    @Test
    void buscarResenaPorIdNoExistente() {
        when(resenaRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resenaService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resena no encontrada", exception.getReason());
    }

    @Test
    void crearResena() {
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);
        when(resenaRepository.save(resena)).thenReturn(resena);

        ResenaResponseDto dto = resenaService.crear(resena);

        assertEquals(1, dto.getId());
        verify(resenaRepository, times(1)).save(resena);
    }

    @Test
    void eliminarResenaExistente() {
        when(resenaRepository.existsById(1)).thenReturn(true);
        resenaService.eliminar(1);

        verify(resenaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarResenaNoExistente() {
        when(resenaRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            resenaService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Resena no encontrada", exception.getReason());
        verify(resenaRepository, never()).deleteById(7);
    }
}
