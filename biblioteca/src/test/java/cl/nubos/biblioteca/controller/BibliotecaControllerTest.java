package cl.nubos.biblioteca.controller;

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

import cl.nubos.biblioteca.dto.BibliotecaResponseDto;
import cl.nubos.biblioteca.dto.UsuarioDto;
import cl.nubos.biblioteca.dto.VideojuegoDto;
import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.service.BibliotecaService;

@WebMvcTest(BibliotecaController.class)
public class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BibliotecaService bibliotecaService;

    private Biblioteca biblioteca;
    private BibliotecaResponseDto responseDto;

    @BeforeEach
    void setUp() {
        biblioteca = new Biblioteca();
        biblioteca.setId(1);
        biblioteca.setUsuario(10);
        biblioteca.setVideojuego(100);
        biblioteca.setFechaAdquisicion(LocalDate.of(2026, 6, 30));
        biblioteca.setHorasJugadas(12);
        biblioteca.setUltimaSesion(LocalDateTime.of(2026, 6, 30, 12, 0, 0));

        responseDto = new BibliotecaResponseDto();
        responseDto.setId(1);
        responseDto.setFechaAdquisicion(LocalDate.of(2026, 6, 30));
        responseDto.setHorasJugadas(12);
        responseDto.setUltimaSesion(LocalDateTime.of(2026, 6, 30, 12, 0, 0));

        UsuarioDto usuarioDto = new UsuarioDto(10, "Juan", 25, "juan@example.com");
        VideojuegoDto videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
        responseDto.setUsuario(usuarioDto);
        responseDto.setVideojuego(videojuegoDto);
    }

    @Test
    void listarBiblioteca() throws Exception {
        List<BibliotecaResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(bibliotecaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/biblioteca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fechaAdquisicion").value("2026-06-30"))
                .andExpect(jsonPath("$[0].horasJugadas").value(12))
                .andExpect(jsonPath("$[0].ultimaSesion").value("2026-06-30T12:00:00"))
                .andExpect(jsonPath("$[0].usuario.id").value(10))
                .andExpect(jsonPath("$[0].usuario.nombre").value("Juan"))
                .andExpect(jsonPath("$[0].videojuego.id").value(100))
                .andExpect(jsonPath("$[0].videojuego.titulo").value("Hollow Knight"));
    }

    @Test
    void noListarBiblioteca() throws Exception {
        List<BibliotecaResponseDto> lista = new ArrayList<>();
        when(bibliotecaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/biblioteca"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerBibliotecaPorId() throws Exception {
        when(bibliotecaService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/biblioteca/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fechaAdquisicion").value("2026-06-30"))
                .andExpect(jsonPath("$.horasJugadas").value(12))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noObtenerBibliotecaPorId() throws Exception {
        when(bibliotecaService.buscarPorId(7))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado"));

        mockMvc.perform(get("/api/v1/biblioteca/7"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarBiblioteca() throws Exception {
        when(bibliotecaService.crear(any(Biblioteca.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/biblioteca")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaAdquisicion\":\"2026-06-30\",\"horasJugadas\":12,\"ultimaSesion\":\"2026-06-30T12:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.horasJugadas").value(12))
                .andExpect(jsonPath("$.usuario.id").value(10))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void actualizarBiblioteca() throws Exception {
        when(bibliotecaService.actualizar(eq(1), any(Biblioteca.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/biblioteca/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaAdquisicion\":\"2026-06-30\",\"horasJugadas\":12,\"ultimaSesion\":\"2026-06-30T12:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.horasJugadas").value(12))
                .andExpect(jsonPath("$.usuario.id").value(10));
    }

    @Test
    void noActualizarBiblioteca() throws Exception {
        when(bibliotecaService.actualizar(eq(7), any(Biblioteca.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado"));

        mockMvc.perform(put("/api/v1/biblioteca/7")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"usuario\":10,\"videojuego\":100,\"fechaAdquisicion\":\"2026-06-30\",\"horasJugadas\":12,\"ultimaSesion\":\"2026-06-30T12:00:00\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarBiblioteca() throws Exception {
        doNothing().when(bibliotecaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/biblioteca/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarBiblioteca() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro de Biblioteca no encontrado"))
                .when(bibliotecaService).eliminar(7);

        mockMvc.perform(delete("/api/v1/biblioteca/7"))
                .andExpect(status().isNotFound());
    }
}
