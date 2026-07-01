package cl.nubos.biblioteca.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.biblioteca.client.UsuarioClient;
import cl.nubos.biblioteca.client.VideojuegoClient;
import cl.nubos.biblioteca.dto.BibliotecaResponseDto;
import cl.nubos.biblioteca.dto.UsuarioDto;
import cl.nubos.biblioteca.dto.VideojuegoDto;
import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.repository.BibliotecaRepository;

@ExtendWith(MockitoExtension.class)
public class BibliotecaServiceTest {

    @Mock
    private BibliotecaRepository bibliotecaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private VideojuegoClient videojuegoClient;

    @InjectMocks
    private BibliotecaService bibliotecaService;

    private Biblioteca biblioteca;
    private UsuarioDto usuarioDto;
    private VideojuegoDto videojuegoDto;

    @BeforeEach
    public void setup() {
        biblioteca = new Biblioteca();
        biblioteca.setId(1);
        biblioteca.setUsuario(10);
        biblioteca.setVideojuego(100);
        biblioteca.setFechaAdquisicion(LocalDate.of(2026, 6, 30));
        biblioteca.setHorasJugadas(12);
        biblioteca.setUltimaSesion(LocalDateTime.of(2026, 6, 30, 12, 0, 0));

        usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
    }

    @Test
    void buscarBibliotecaPorIdExistente() {
        when(bibliotecaRepository.findById(1)).thenReturn(Optional.of(biblioteca));
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);

        BibliotecaResponseDto dto = bibliotecaService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals(LocalDate.of(2026, 6, 30), dto.getFechaAdquisicion());
        assertEquals(12, dto.getHorasJugadas());
        assertEquals(10, dto.getUsuario().getId());
        assertEquals(100, dto.getVideojuego().getId());
    }

    @Test
    void buscarBibliotecaPorIdNoExistente() {
        when(bibliotecaRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            bibliotecaService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Registro de Biblioteca no encontrado", exception.getReason());
    }

    @Test
    void crearBiblioteca() {
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);
        when(bibliotecaRepository.save(biblioteca)).thenReturn(biblioteca);

        BibliotecaResponseDto dto = bibliotecaService.crear(biblioteca);

        assertEquals(1, dto.getId());
        verify(bibliotecaRepository, times(1)).save(biblioteca);
    }

    @Test
    void eliminarBibliotecaExistente() {
        when(bibliotecaRepository.existsById(1)).thenReturn(true);
        bibliotecaService.eliminar(1);

        verify(bibliotecaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarBibliotecaNoExistente() {
        when(bibliotecaRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            bibliotecaService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Registro de Biblioteca no encontrado", exception.getReason());
        verify(bibliotecaRepository, never()).deleteById(7);
    }
}
