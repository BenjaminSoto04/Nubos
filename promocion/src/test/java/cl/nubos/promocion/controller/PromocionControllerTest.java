package cl.nubos.promocion.controller;

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

import cl.nubos.promocion.dto.PromocionResponseDto;
import cl.nubos.promocion.dto.VideojuegoDto;
import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.service.PromocionService;

@WebMvcTest(PromocionController.class)
public class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PromocionService promocionService;

    private Promocion promocion;
    private PromocionResponseDto responseDto;

    @BeforeEach
    void setUp() {
        promocion = new Promocion();
        promocion.setId(1);
        promocion.setVideojuego(100);
        promocion.setPorcentajeDescuento(20);
        promocion.setFechaInicio(LocalDate.of(2026, 6, 30));
        promocion.setFechaFin(LocalDate.of(2026, 7, 30));
        promocion.setCodigoPromocional("SUMMER26");
        promocion.setActiva(true);

        responseDto = new PromocionResponseDto();
        responseDto.setId(1);
        responseDto.setPorcentajeDescuento(20);
        responseDto.setFechaInicio(LocalDate.of(2026, 6, 30));
        responseDto.setFechaFin(LocalDate.of(2026, 7, 30));
        responseDto.setCodigoPromocional("SUMMER26");
        responseDto.setActiva(true);

        VideojuegoDto videojuegoDto = new VideojuegoDto(100, "Hollow Knight", 15.0);
        responseDto.setVideojuego(videojuegoDto);
    }

    @Test
    void listarPromociones() throws Exception {
        List<PromocionResponseDto> lista = new ArrayList<>();
        lista.add(responseDto);
        when(promocionService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/promociones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].porcentajeDescuento").value(20))
                .andExpect(jsonPath("$[0].codigoPromocional").value("SUMMER26"))
                .andExpect(jsonPath("$[0].activa").value(true))
                .andExpect(jsonPath("$[0].videojuego.id").value(100));
    }

    @Test
    void noListarPromociones() throws Exception {
        List<PromocionResponseDto> lista = new ArrayList<>();
        when(promocionService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/promociones"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerPromocionPorId() throws Exception {
        when(promocionService.buscarPorId(1)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/promociones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.porcentajeDescuento").value(20))
                .andExpect(jsonPath("$.codigoPromocional").value("SUMMER26"))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noObtenerPromocionPorId() throws Exception {
        when(promocionService.buscarPorId(7))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));

        mockMvc.perform(get("/api/v1/promociones/7"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarPromocion() throws Exception {
        when(promocionService.crear(any(Promocion.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/promociones")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"videojuego\":100,\"porcentajeDescuento\":20,\"fechaInicio\":\"2026-06-30\",\"fechaFin\":\"2026-07-30\",\"codigoPromocional\":\"SUMMER26\",\"activa\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.porcentajeDescuento").value(20))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void actualizarPromocion() throws Exception {
        when(promocionService.actualizar(eq(1), any(Promocion.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/promociones/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"videojuego\":100,\"porcentajeDescuento\":20,\"fechaInicio\":\"2026-06-30\",\"fechaFin\":\"2026-07-30\",\"codigoPromocional\":\"SUMMER26\",\"activa\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.porcentajeDescuento").value(20))
                .andExpect(jsonPath("$.videojuego.id").value(100));
    }

    @Test
    void noActualizarPromocion() throws Exception {
        when(promocionService.actualizar(eq(7), any(Promocion.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));

        mockMvc.perform(put("/api/v1/promociones/7")
                .contentType("application/json")
                .content(
                        "{\"id\":7,\"videojuego\":100,\"porcentajeDescuento\":20,\"fechaInicio\":\"2026-06-30\",\"fechaFin\":\"2026-07-30\",\"codigoPromocional\":\"SUMMER26\",\"activa\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPromocion() throws Exception {
        doNothing().when(promocionService).eliminar(1);

        mockMvc.perform(delete("/api/v1/promociones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarPromocion() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"))
                .when(promocionService).eliminar(7);

        mockMvc.perform(delete("/api/v1/promociones/7"))
                .andExpect(status().isNotFound());
    }
}
