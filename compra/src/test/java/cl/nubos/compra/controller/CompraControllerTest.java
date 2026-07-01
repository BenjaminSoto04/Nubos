package cl.nubos.compra.controller;

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

import cl.nubos.compra.dto.CompraResponseDto;
import cl.nubos.compra.dto.UsuarioDto;
import cl.nubos.compra.dto.VideojuegoDto;
import cl.nubos.compra.model.Compra;
import cl.nubos.compra.service.CompraService;

@WebMvcTest(CompraController.class)
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompraService compraService;

    private Compra compra;
    private CompraResponseDto responseDto;

    @BeforeEach
    void setUp() {
        compra = new Compra();
        compra.setId(1);
        compra.setUsuario(10);
        compra.setVideojuego(100);
        compra.setFechaCompra(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        compra.setMontoTotal(14990.0);
        compra.setMetodoPago("Tarjeta de Crédito");
        compra.setEstado("Completada");

        responseDto = new CompraResponseDto();
        responseDto.setId(1);
        responseDto.setFechaCompra(LocalDateTime.of(2026, 6, 30, 12, 0, 0));
        responseDto.setMontoTotal(14990.0);
        responseDto.setMetodoPago("Tarjeta de Crédito");
        responseDto.setEstado("Completada");

        UsuarioDto usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        VideojuegoDto videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
        responseDto.setUsuario(usuarioDto);
        responseDto.setVideojuego(videojuegoDto);
    }

    @Test
    void listarCompras() throws Exception {
        List<CompraResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(compraService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].montoTotal").value(14990.0))
                .andExpect(jsonPath("$[0].metodoPago").value("Tarjeta de Crédito"))
                .andExpect(jsonPath("$[0].estado").value("Completada"))
                .andExpect(jsonPath("$[0].usuario.id").value(10))
                .andExpect(jsonPath("$[0].videojuego.id").value(100));
    }

    @Test
    void noListarCompras() throws Exception {
        List<CompraResponseDto> lista = new ArrayList<>();
        when(compraService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/compras"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerCompraPorId() throws Exception {
        when(compraService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/compras/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.montoTotal").value(14990.0))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noObtenerCompraPorId() throws Exception {
        when(compraService.buscarPorId(1))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));

        mockMvc.perform(get("/api/v1/compras/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarCompra() throws Exception {
        when(compraService.crear(any(Compra.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/compras")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaCompra\":\"2026-06-30T12:00:00\",\"montoTotal\":14990.0,\"metodoPago\":\"Tarjeta de Crédito\",\"estado\":\"Completada\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.montoTotal").value(14990))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void actualizarCompra() throws Exception {
        when(compraService.actualizar(eq(1), any(Compra.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/compras/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaCompra\":\"2026-06-30T12:00:00\",\"montoTotal\":14990.0,\"metodoPago\":\"Tarjeta de Crédito\",\"estado\":\"Completada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.montoTotal").value(14990.0))
                .andExpect(jsonPath("$.usuario.id").value(10));
    }

    @Test
    void noActualizarCompra() throws Exception {
        when(compraService.actualizar(eq(1), any(Compra.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"));

        mockMvc.perform(put("/api/v1/compras/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaCompra\":\"2026-06-30T12:00:00\",\"montoTotal\":14990.0,\"metodoPago\":\"Tarjeta de Crédito\",\"estado\":\"Completada\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarCompra() throws Exception {
        doNothing().when(compraService).eliminar(1);

        mockMvc.perform(delete("/api/v1/compras/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarCompra() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra no encontrada"))
                .when(compraService).eliminar(1);

        mockMvc.perform(delete("/api/v1/compras/1"))
                .andExpect(status().isNotFound());
    }
}
