package cl.nubos.soporte.controller;

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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.nubos.soporte.model.Soporte;
import cl.nubos.soporte.service.SoporteService;

@WebMvcTest(SoporteController.class)
public class SoporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SoporteService soporteService;

    private Soporte soporte;

    @BeforeEach
    void setUp() {
        soporte = new Soporte();
        soporte.setId(1);
        soporte.setTitulo("Error de Login");
        soporte.setDescripcion("No puedo iniciar sesión");
        soporte.setFechaReporte(LocalDate.of(2026, 6, 30));
        soporte.setEstado("Abierto");
    }

    @Test
    void listarReportes() throws Exception {
        List<Soporte> lista = new ArrayList<>();
        lista.add(soporte);
        when(soporteService.getAllReportes()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/soporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Error de Login"))
                .andExpect(jsonPath("$[0].descripcion").value("No puedo iniciar sesión"))
                .andExpect(jsonPath("$[0].fechaReporte").value("2026-06-30"))
                .andExpect(jsonPath("$[0].estado").value("Abierto"));
    }

    @Test
    void noListarReportes() throws Exception {
        List<Soporte> lista = new ArrayList<>();
        when(soporteService.getAllReportes()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/soporte"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerReportePorId() throws Exception {
        when(soporteService.getReporteById(1)).thenReturn(soporte);

        mockMvc.perform(get("/api/v1/soporte/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Error de Login"))
                .andExpect(jsonPath("$.descripcion").value("No puedo iniciar sesión"))
                .andExpect(jsonPath("$.fechaReporte").value("2026-06-30"))
                .andExpect(jsonPath("$.estado").value("Abierto"));
    }

    @Test
    void noObtenerReportePorId() throws Exception {
        when(soporteService.getReporteById(1)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/v1/soporte/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearReporte() throws Exception {
        when(soporteService.crearReporte(any(Soporte.class))).thenReturn(soporte);

        mockMvc.perform(post("/api/v1/soporte")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Error de Login\",\"descripcion\":\"No puedo iniciar sesión\",\"fechaReporte\":\"2026-06-30\",\"estado\":\"Abierto\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Error de Login"))
                .andExpect(jsonPath("$.descripcion").value("No puedo iniciar sesión"))
                .andExpect(jsonPath("$.fechaReporte").value("2026-06-30"))
                .andExpect(jsonPath("$.estado").value("Abierto"));
    }

    @Test
    void actualizarReporte() throws Exception {
        when(soporteService.actualizarReporte(eq(1), any(Soporte.class))).thenReturn(soporte);

        mockMvc.perform(put("/api/v1/soporte/1")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Error de Login\",\"descripcion\":\"No puedo iniciar sesión\",\"fechaReporte\":\"2026-06-30\",\"estado\":\"Abierto\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Error de Login"))
                .andExpect(jsonPath("$.descripcion").value("No puedo iniciar sesión"))
                .andExpect(jsonPath("$.fechaReporte").value("2026-06-30"))
                .andExpect(jsonPath("$.estado").value("Abierto"));
    }

    @Test
    void noActualizarReporte() throws Exception {
        when(soporteService.actualizarReporte(eq(1), any(Soporte.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(put("/api/v1/soporte/1")
                .contentType("application/json")
                .content("{\"id\":1,\"titulo\":\"Error de Login\",\"descripcion\":\"No puedo iniciar sesión\",\"fechaReporte\":\"2026-06-30\",\"estado\":\"Abierto\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarReporte() throws Exception {
        doNothing().when(soporteService).eliminarReporte(1);

        mockMvc.perform(delete("/api/v1/soporte/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarReporte() throws Exception {
        doThrow(RuntimeException.class).when(soporteService).eliminarReporte(1);

        mockMvc.perform(delete("/api/v1/soporte/1"))
                .andExpect(status().isNotFound());
    }
}
