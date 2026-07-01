package cl.nubos.videojuego.service;

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

import cl.nubos.videojuego.client.CategoriaClient;
import cl.nubos.videojuego.client.DesarrolladorClient;
import cl.nubos.videojuego.dto.CategoriaDto;
import cl.nubos.videojuego.dto.DesarrolladorDto;
import cl.nubos.videojuego.dto.VideojuegoResponseDto;
import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.repository.VideojuegoRepository;

@ExtendWith(MockitoExtension.class)
public class VideojuegoServiceTest {

    @Mock
    private VideojuegoRepository videojuegoRepository;

    @Mock
    private DesarrolladorClient desarrolladorClient;

    @Mock
    private CategoriaClient categoriaClient;

    @InjectMocks
    private VideojuegoService videojuegoService;

    private Videojuego videojuego;
    private DesarrolladorDto desarrolladorDto;
    private CategoriaDto categoriaDto;

    @BeforeEach
    public void setup() {
        videojuego = new Videojuego();
        videojuego.setId(1);
        videojuego.setTitulo("Hollow Knight");
        videojuego.setDescripcion("Metroidvania");
        videojuego.setPrecio(15.0);
        videojuego.setFechaLanzamiento(LocalDate.of(2017, 2, 24));
        videojuego.setDesarrollador(5);
        videojuego.setCategoria(3);
        videojuego.setClasificacionEdad("Everyone");

        desarrolladorDto = new DesarrolladorDto(5, "Team Cherry", "Estudio Indie", "cherry@team.com");
        categoriaDto = new CategoriaDto(3, "Acción", "Juegos de Acción");
    }

    @Test
    void buscarVideojuegoPorIdExistente() {
        when(videojuegoRepository.findById(1)).thenReturn(Optional.of(videojuego));
        when(desarrolladorClient.obtenerPorId(5)).thenReturn(desarrolladorDto);
        when(categoriaClient.obtenerPorId(3)).thenReturn(categoriaDto);

        VideojuegoResponseDto dto = videojuegoService.buscarPorId(1);

        assertEquals(1, dto.getId());
        assertEquals("Hollow Knight", dto.getTitulo());
        assertEquals(15.0, dto.getPrecio());
        assertEquals(5, dto.getDesarrollador().getId());
        assertEquals(3, dto.getCategoria().getId());
    }

    @Test
    void buscarVideojuegoPorIdNoExistente() {
        when(videojuegoRepository.findById(7)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            videojuegoService.buscarPorId(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Videojuego no encontrado", exception.getReason());
    }

    @Test
    void crearVideojuego() {
        when(desarrolladorClient.obtenerPorId(5)).thenReturn(desarrolladorDto);
        when(categoriaClient.obtenerPorId(3)).thenReturn(categoriaDto);
        when(videojuegoRepository.save(videojuego)).thenReturn(videojuego);

        VideojuegoResponseDto dto = videojuegoService.crear(videojuego);

        assertEquals(1, dto.getId());
        verify(videojuegoRepository, times(1)).save(videojuego);
    }

    @Test
    void eliminarVideojuegoExistente() {
        when(videojuegoRepository.existsById(1)).thenReturn(true);
        videojuegoService.eliminar(1);

        verify(videojuegoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarVideojuegoNoExistente() {
        when(videojuegoRepository.existsById(7)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            videojuegoService.eliminar(7);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Videojuego no encontrado", exception.getReason());
        verify(videojuegoRepository, never()).deleteById(7);
    }
}
