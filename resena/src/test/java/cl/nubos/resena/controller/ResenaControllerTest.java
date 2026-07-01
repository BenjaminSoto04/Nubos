package cl.nubos.resena.controller;

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

import java.time.LocalDate;
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

import cl.nubos.resena.dto.ResenaResponseDto;
import cl.nubos.resena.dto.UsuarioDto;
import cl.nubos.resena.dto.VideojuegoDto;
import cl.nubos.resena.model.Resena;
import cl.nubos.resena.service.ResenaService;

@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    private Resena resena;
    private ResenaResponseDto responseDto;

    @BeforeEach
    void setUp() {
        resena = new Resena();
        resena.setId(1);
        resena.setUsuario(10);
        resena.setVideojuego(100);
        resena.setCalificacion(5);
        resena.setComentario("Excelente juego");
        resena.setFechaResena(LocalDate.of(2026, 6, 30));
        resena.setRecomendado(true);

        responseDto = new ResenaResponseDto();
        responseDto.setId(1);
        responseDto.setCalificacion(5);
        responseDto.setComentario("Excelente juego");
        responseDto.setFechaResena(LocalDate.of(2026, 6, 30));
        responseDto.setRecomendado(true);

        UsuarioDto usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        VideojuegoDto videojuegoDto = new VideojuegoDto(100, "Hollow Knight", "Metroidvania", 15.0, LocalDate.of(2017, 2, 24), "Everyone");
        responseDto.setUsuario(usuarioDto);
        responseDto.setVideojuego(videojuegoDto);
    }

    @Test
    void listarResenas() throws Exception {
        List<ResenaResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(resenaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].calificacion").value(5))
                .andExpect(jsonPath("$[0].comentario").value("Excelente juego"))
                .andExpect(jsonPath("$[0].recomendado").value(true))
                .andExpect(jsonPath("$[0].usuario.id").value(10))
                .andExpect(jsonPath("$[0].videojuego.id").value(100));
    }

    @Test
    void noListarResenas() throws Exception {
        List<ResenaResponseDto> lista = new ArrayList<>();
        when(resenaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/resenas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerResenaPorId() throws Exception {
        when(resenaService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.comentario").value("Excelente juego"))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noObtenerResenaPorId() throws Exception {
        when(resenaService.buscarPorId(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));

        mockMvc.perform(get("/api/v1/resenas/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarResena() throws Exception {
        when(resenaService.crear(any(Resena.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/resenas")
                .contentType("application/json")
                .content("{\"id\":1,\"usuario\":10,\"videojuego\":100,\"calificacion\":5,\"comentario\":\"Excelente juego\",\"fechaResena\":\"2026-06-30\",\"recomendado\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void actualizarResena() throws Exception {
        when(resenaService.actualizar(eq(1), any(Resena.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/resenas/1")
                .contentType("application/json")
                .content("{\"id\":1,\"usuario\":10,\"videojuego\":100,\"calificacion\":5,\"comentario\":\"Excelente juego\",\"fechaResena\":\"2026-06-30\",\"recomendado\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.usuario.id").value(10));
    }

    @Test
    void noActualizarResena() throws Exception {
        when(resenaService.actualizar(eq(1), any(Resena.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"));

        mockMvc.perform(put("/api/v1/resenas/1")
                .contentType("application/json")
                .content("{\"id\":1,\"usuario\":10,\"videojuego\":100,\"calificacion\":5,\"comentario\":\"Excelente juego\",\"fechaResena\":\"2026-06-30\",\"recomendado\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarResena() throws Exception {
        doNothing().when(resenaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/resenas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarResena() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resena no encontrada"))
                .when(resenaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/resenas/1"))
                .andExpect(status().isNotFound());
    }
}
