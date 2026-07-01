package cl.nubos.desarrollador.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.nubos.desarrollador.model.Desarrollador;
import cl.nubos.desarrollador.service.DesarrolladorService;

@WebMvcTest(DesarrolladorController.class)
public class DesarrolladorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DesarrolladorService desarrolladorService;

    private Desarrollador desarrollador;

    @BeforeEach
    void setUp() {
        desarrollador = new Desarrollador();
        desarrollador.setId(1);
        desarrollador.setNombre("Juan");
        desarrollador.setEstudio("Team Cherry");
        desarrollador.setCorreo("juarry@gmail.com");
        desarrollador.setPaís("Chile");
        desarrollador.setContraseña("password");
    }

    @Test
    void listarDesarrolladores() throws Exception {
        List<Desarrollador> desarrolladores = new ArrayList<>();
        desarrolladores.add(desarrollador);
        when(desarrolladorService.getAllDesarrolladores()).thenReturn(desarrolladores);
        mockMvc.perform(get("/api/v1/desarrolladores")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].estudio").value("Team Cherry"))
                .andExpect(jsonPath("$[0].correo").value("juarry@gmail.com"))
                .andExpect(jsonPath("$[0].país").value("Chile"))
                .andExpect(jsonPath("$[0].contraseña").value("password"));
    }

    @Test
    void noListarDesarrolladores() throws Exception {
        List<Desarrollador> desarrolladores = new ArrayList<>();
        when(desarrolladorService.getAllDesarrolladores()).thenReturn(desarrolladores);
        mockMvc.perform(get("/api/v1/desarrolladores")).andExpect(status().isNoContent());
    }

    @Test
    void obtenerDesarrolladorPorId() throws Exception {
        when(desarrolladorService.getDesarrolladorById(1)).thenReturn(desarrollador);
        mockMvc.perform(get("/api/v1/desarrolladores/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.estudio").value("Team Cherry"))
                .andExpect(jsonPath("$.correo").value("juarry@gmail.com"))
                .andExpect(jsonPath("$.país").value("Chile"))
                .andExpect(jsonPath("$.contraseña").value("password"));
    }

    @Test
    void noObtenerDesarrolladorPorId() throws Exception {
        when(desarrolladorService.getDesarrolladorById(1)).thenThrow(RuntimeException.class);
        mockMvc.perform(get("/api/v1/desarrolladores/1")).andExpect(status().isNotFound());
    }

    @Test
    void agregarDesarrollador() throws Exception {
        when(desarrolladorService.createDesarrollador(desarrollador)).thenReturn(desarrollador);
        mockMvc.perform(post("/api/v1/desarrolladores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"id\":1,\"nombre\":\"Juan\",\"estudio\":\"Team Cherry\",\"correo\":\"juarry@gmail.com\",\"país\":\"Chile\",\"contraseña\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.estudio").value("Team Cherry"))
                .andExpect(jsonPath("$.correo").value("juarry@gmail.com"))
                .andExpect(jsonPath("$.país").value("Chile"))
                .andExpect(jsonPath("$.contraseña").value("password"));
    }

    @Test
    void actualizarDesarrollador() throws Exception {
        when(desarrolladorService.updateDesarrollador(1, desarrollador)).thenReturn(desarrollador);
        mockMvc.perform(put("/api/v1/desarrolladores/1").contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"id\":1,\"nombre\":\"Juan\",\"estudio\":\"Team Cherry\",\"correo\":\"juarry@gmail.com\",\"país\":\"Chile\",\"contraseña\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.estudio").value("Team Cherry"))
                .andExpect(jsonPath("$.correo").value("juarry@gmail.com"))
                .andExpect(jsonPath("$.país").value("Chile"))
                .andExpect(jsonPath("$.contraseña").value("password"));
    }

    @Test
    void noActualizarDesarrollador() throws Exception {
        when(desarrolladorService.updateDesarrollador(9, desarrollador)).thenThrow(RuntimeException.class);
        mockMvc.perform(put("/api/v1/desarrolladores/9").contentType("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"id\":9,\"nombre\":\"Juan\",\"estudio\":\"Team Cherry\",\"correo\":\"juarry@gmail.com\",\"país\":\"Chile\",\"contraseña\":\"password\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarDesarrollador() throws Exception {
        doNothing().when(desarrolladorService).deleteDesarrollador(1);
        mockMvc.perform(delete("/api/v1/desarrolladores/1")).andExpect(status().isNoContent());
    }

    @Test
    void noEliminarDesarrollador() throws Exception {
        doThrow(RuntimeException.class).when(desarrolladorService).deleteDesarrollador(9);
        mockMvc.perform(delete("/api/v1/desarrolladores/1")).andExpect(status().isNotFound());
    }
}
