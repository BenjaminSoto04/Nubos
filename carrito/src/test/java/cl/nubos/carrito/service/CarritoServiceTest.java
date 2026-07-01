package cl.nubos.carrito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

import cl.nubos.carrito.client.UsuarioClient;
import cl.nubos.carrito.client.VideojuegoClient;
import cl.nubos.carrito.dto.CarritoResponseDto;
import cl.nubos.carrito.dto.UsuarioDto;
import cl.nubos.carrito.dto.VideojuegoDto;
import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.repository.CarritoRepository;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private VideojuegoClient videojuegoClient;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;
    private UsuarioDto usuarioDto;
    private VideojuegoDto videojuegoDto;

    @BeforeEach
    public void setup() {
        carrito = new Carrito();
        carrito.setId(1);
        carrito.setUsuario(10);
        carrito.setVideojuego(100);
        carrito.setCantidad(2);
        carrito.setFechaAgregado(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        carrito.setPrecioUnitario(29.99);

        usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
    }

    @Test
    void buscarCarritoPorIdExistente() {
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);

        CarritoResponseDto dto = carritoService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals(2, dto.getCantidad());
        assertEquals(29.99, dto.getPrecioUnitario());
        assertEquals(10, dto.getUsuario().getId());
        assertEquals(100, dto.getVideojuego().getId());
    }

    @Test
    void buscarCarritoPorIdNoExistente() {
        when(carritoRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            carritoService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item de Carrito no encontrado", exception.getReason());
    }

    @Test
    void crearCarrito() {
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        CarritoResponseDto dto = carritoService.crear(carrito);

        assertEquals(1, dto.getId());
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    void eliminarCarritoExistente() {
        when(carritoRepository.existsById(1)).thenReturn(true);
        carritoService.eliminar(1);

        verify(carritoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarCarritoNoExistente() {
        when(carritoRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            carritoService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Item de Carrito no encontrado", exception.getReason());
        verify(carritoRepository, never()).deleteById(7);
    }
}
