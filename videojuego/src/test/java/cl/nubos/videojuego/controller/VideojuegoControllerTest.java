package cl.nubos.videojuego.controller;

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

import cl.nubos.videojuego.dto.CategoriaDto;
import cl.nubos.videojuego.dto.DesarrolladorDto;
import cl.nubos.videojuego.dto.VideojuegoResponseDto;
import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.service.VideojuegoService;

@WebMvcTest(VideojuegoController.class)
public class VideojuegoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VideojuegoService videojuegoService;

    private Videojuego videojuego;
    private VideojuegoResponseDto responseDto;

    @BeforeEach
    void setUp() {
        videojuego = new Videojuego();
        videojuego.setId(1);
        videojuego.setTitulo("Hollow Knight");
        videojuego.setDescripcion("Metroidvania");
        videojuego.setPrecio(15.0);
        videojuego.setFechaLanzamiento(LocalDate.of(2017, 2, 24));
        videojuego.setDesarrollador(5);
        videojuego.setCategoria(3);
        videojuego.setClasificacionEdad("Everyone");

        responseDto = new VideojuegoResponseDto();
        responseDto.setId(1);
        responseDto.setTitulo("Hollow Knight");
        responseDto.setDescripcion("Metroidvania");
        responseDto.setPrecio(15.0);
        responseDto.setFechaLanzamiento(LocalDate.of(2017, 2, 24));
        responseDto.setClasificacionEdad("Everyone");

        DesarrolladorDto desarrolladorDto = new DesarrolladorDto(5, "Team Cherry", "Estudio Indie", "cherry@team.com");
        CategoriaDto categoriaDto = new CategoriaDto(3, "Acción", "Juegos de Acción");
        responseDto.setDesarrollador(desarrolladorDto);
        responseDto.setCategoria(categoriaDto);
    }

    @Test
    void listarVideojuegos() throws Exception {
        List<VideojuegoResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(videojuegoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/videojuegos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Hollow Knight"))
                .andExpect(jsonPath("$[0].descripcion").value("Metroidvania"))
                .andExpect(jsonPath("$[0].precio").value(15.0))
                .andExpect(jsonPath("$[0].fechaLanzamiento").value("2017-02-24"))
                .andExpect(jsonPath("$[0].clasificacionEdad").value("Everyone"))
                .andExpect(jsonPath("$[0].desarrollador.id").value(5))
                .andExpect(jsonPath("$[0].categoria.id").value(3));
    }

    @Test
    void noListarVideojuegos() throws Exception {
        List<VideojuegoResponseDto> lista = new ArrayList<>();
        when(videojuegoService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/videojuegos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerVideojuegoPorId() throws Exception {
        when(videojuegoService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/videojuegos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Hollow Knight"))
                .andExpect(jsonPath("$.precio").value(15.0))
                .andExpect(jsonPath("$.desarrollador.id").value(5))
                .andExpect(jsonPath("$.categoria.id").value(3));
    }

    @Test
    void noObtenerVideojuegoPorId() throws Exception {
        when(videojuegoService.buscarPorId(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));

        mockMvc.perform(get("/api/v1/videojuegos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarVideojuego() throws Exception {
        when(videojuegoService.crear(any(Videojuego.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/videojuegos")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Hollow Knight\",\"descripcion\":\"Metroidvania\",\"precio\":15.0,\"fechaLanzamiento\":\"2017-02-24\",\"desarrollador\":5,\"categoria\":3,\"clasificacionEdad\":\"Everyone\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Hollow Knight"))
                .andExpect(jsonPath("$.precio").value(15.0))
                .andExpect(jsonPath("$.desarrollador.id").value(5))
                .andExpect(jsonPath("$.categoria.id").value(3));
    }

    @Test
    void actualizarVideojuego() throws Exception {
        when(videojuegoService.actualizar(eq(1), any(Videojuego.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/videojuegos/1")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Hollow Knight\",\"descripcion\":\"Metroidvania\",\"precio\":15.0,\"fechaLanzamiento\":\"2017-02-24\",\"desarrollador\":5,\"categoria\":3,\"clasificacionEdad\":\"Everyone\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Hollow Knight"))
                .andExpect(jsonPath("$.precio").value(15.0));
    }

    @Test
    void noActualizarVideojuego() throws Exception {
        when(videojuegoService.actualizar(eq(1), any(Videojuego.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"));

        mockMvc.perform(put("/api/v1/videojuegos/1")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Hollow Knight\",\"descripcion\":\"Metroidvania\",\"precio\":15.0,\"fechaLanzamiento\":\"2017-02-24\",\"desarrollador\":5,\"categoria\":3,\"clasificacionEdad\":\"Everyone\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarVideojuego() throws Exception {
        doNothing().when(videojuegoService).eliminar(1);

        mockMvc.perform(delete("/api/v1/videojuegos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarVideojuego() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Videojuego no encontrado"))
                .when(videojuegoService).eliminar(1);

        mockMvc.perform(delete("/api/v1/videojuegos/1"))
                .andExpect(status().isNotFound());
    }
}
