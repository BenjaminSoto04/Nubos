package cl.nubos.carrito.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import cl.nubos.carrito.dto.CarritoResponseDto;
import cl.nubos.carrito.dto.UsuarioDto;
import cl.nubos.carrito.dto.VideojuegoDto;
import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.service.CarritoService;

@WebMvcTest(CarritoController.class)
public class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService carritoService;

    private Carrito carrito;
    private CarritoResponseDto responseDto;

    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        carrito.setId(1);
        carrito.setUsuario(10);
        carrito.setVideojuego(100);
        carrito.setCantidad(2);
        carrito.setFechaAgregado(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        carrito.setPrecioUnitario(29.99);

        responseDto = new CarritoResponseDto();
        responseDto.setId(1);
        responseDto.setCantidad(2);
        responseDto.setFechaAgregado(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        responseDto.setPrecioUnitario(29.99);

        UsuarioDto usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        VideojuegoDto videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
        responseDto.setUsuario(usuarioDto);
        responseDto.setVideojuego(videojuegoDto);
    }

    @Test
    void listarCarrito() throws Exception {
        List<CarritoResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(carritoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/carrito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cantidad").value(2))
                .andExpect(jsonPath("$[0].precioUnitario").value(29.99))
                .andExpect(jsonPath("$[0].fechaAgregado").value("2026-06-30T12:00:00"))
                .andExpect(jsonPath("$[0].usuario.id").value(10))
                .andExpect(jsonPath("$[0].videojuego.id").value(100));
    }

    @Test
    void noListarCarrito() throws Exception {
        List<CarritoResponseDto> lista = new ArrayList<>();
        when(carritoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/carrito"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerCarritoPorId() throws Exception {
        when(carritoService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.precioUnitario").value(29.99))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noObtenerCarritoPorId() throws Exception {
        when(carritoService.buscarPorId(7))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));

        mockMvc.perform(get("/api/v1/carrito/7"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarCarrito() throws Exception {
        when(carritoService.crear(any(Carrito.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/carrito")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"cantidad\":2,\"fechaAgregado\":\"2026-06-30T12:00:00\",\"precioUnitario\":29.99}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void actualizarCarrito() throws Exception {
        when(carritoService.actualizar(eq(1), any(Carrito.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/carrito/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"cantidad\":2,\"fechaAgregado\":\"2026-06-30T12:00:00\",\"precioUnitario\":29.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.usuario.id").value(10));
    }

    @Test
    void noActualizarCarrito() throws Exception {
        when(carritoService.actualizar(eq(7), any(Carrito.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"));

        mockMvc.perform(put("/api/v1/carrito/7")
                .contentType("application/json")
                .content(
                        "{\"id\":7,\"usuario\":10,\"videojuego\":100,\"cantidad\":2,\"fechaAgregado\":\"2026-06-30T12:00:00\",\"precioUnitario\":29.99}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarCarrito() throws Exception {
        doNothing().when(carritoService).eliminar(1);

        mockMvc.perform(delete("/api/v1/carrito/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarCarrito() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Item de Carrito no encontrado"))
                .when(carritoService).eliminar(7);

        mockMvc.perform(delete("/api/v1/carrito/7"))
                .andExpect(status().isNotFound());
    }
}
