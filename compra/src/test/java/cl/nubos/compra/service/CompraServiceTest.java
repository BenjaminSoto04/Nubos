package cl.nubos.compra.service;

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

import cl.nubos.compra.client.UsuarioClient;
import cl.nubos.compra.client.VideojuegoClient;
import cl.nubos.compra.dto.CompraResponseDto;
import cl.nubos.compra.dto.UsuarioDto;
import cl.nubos.compra.dto.VideojuegoDto;
import cl.nubos.compra.model.Compra;
import cl.nubos.compra.repository.CompraRepository;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private VideojuegoClient videojuegoClient;

    @InjectMocks
    private CompraService compraService;

    private Compra compra;
    private UsuarioDto usuarioDto;
    private VideojuegoDto videojuegoDto;

    @BeforeEach
    public void setup() {
        compra = new Compra();
        compra.setId(1);
        compra.setUsuario(10);
        compra.setVideojuego(100);
        compra.setFechaCompra(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        compra.setMontoTotal(14990.0);
        compra.setMetodoPago("Tarjeta de Crédito");
        compra.setEstado("Completada");

        usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
    }

    @Test
    void buscarCompraPorIdExistente() {
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra));
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);

        CompraResponseDto dto = compraService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals(14990.0, dto.getMontoTotal());
        assertEquals("Tarjeta de Crédito", dto.getMetodoPago());
        assertEquals(10, dto.getUsuario().getId());
        assertEquals(100, dto.getVideojuego().getId());
    }

    @Test
    void buscarCompraPorIdNoExistente() {
        when(compraRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            compraService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Compra no encontrada", exception.getReason());
    }

    @Test
    void crearCompra() {
        when(usuarioClient.obtenerPorId(10)).thenReturn(usuarioDto);
        when(videojuegoClient.obtenerPorId(100)).thenReturn(videojuegoDto);
        when(compraRepository.save(compra)).thenReturn(compra);

        CompraResponseDto dto = compraService.crear(compra);

        assertEquals(1, dto.getId());
        verify(compraRepository, times(1)).save(compra);
    }

    @Test
    void eliminarCompraExistente() {
        when(compraRepository.existsById(1)).thenReturn(true);
        compraService.eliminar(1);

        verify(compraRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarCompraNoExistente() {
        when(compraRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            compraService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Compra no encontrada", exception.getReason());
        verify(compraRepository, never()).deleteById(7);
    }
}
