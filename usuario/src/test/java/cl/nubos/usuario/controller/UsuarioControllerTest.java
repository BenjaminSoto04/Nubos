package cl.nubos.usuario.controller;

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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.nubos.usuario.model.Usuario;
import cl.nubos.usuario.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setEdad(25);
        usuario.setCorreo("juan@gmail.com");
        usuario.setContraseña("eol324");
    }

    @Test
    void listarUsuarios() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        lista.add(usuario);
        when(usuarioService.listarUsuarios()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].edad").value(25))
                .andExpect(jsonPath("$[0].correo").value("juan@gmail.com"))
                .andExpect(jsonPath("$[0].contraseña").value("eol324"));
    }

    @Test
    void noListarUsuarios() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        when(usuarioService.listarUsuarios()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerUsuarioPorId() throws Exception {
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.edad").value(25))
                .andExpect(jsonPath("$.correo").value("juan@gmail.com"));
    }

    @Test
    void noObtenerUsuarioPorId() throws Exception {
        when(usuarioService.buscarUsuarioPorId(1)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarUsuario() throws Exception {
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"nombre\":\"Juan\",\"edad\":25,\"correo\":\"juan@gmail.com\",\"contraseña\":\"eol324\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.edad").value(25))
                .andExpect(jsonPath("$.correo").value("juan@gmail.com"));
    }

    @Test
    void actualizarUsuario() throws Exception {
        when(usuarioService.actualizarUsuario(eq(1), any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"nombre\":\"Juan\",\"edad\":25,\"correo\":\"juan@gmail.com\",\"contraseña\":\"eol324\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.edad").value(25))
                .andExpect(jsonPath("$.correo").value("juan@gmail.com"));
    }

    @Test
    void noActualizarUsuario() throws Exception {
        when(usuarioService.actualizarUsuario(eq(1), any(Usuario.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType("application/json")
                .content(
                        "{\"id\":1,\"nombre\":\"Juan\",\"edad\":25,\"correo\":\"juan@gmail.com\",\"contraseña\":\"eol324\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarUsuario() throws Exception {
        doThrow(RuntimeException.class).when(usuarioService).eliminarUsuario(1);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNotFound());
    }
}
