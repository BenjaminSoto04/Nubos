package cl.nubos.categoria.controller;

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

import cl.nubos.categoria.model.Categoria;
import cl.nubos.categoria.service.CategoriaService;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Acción");
        categoria.setDescripcion("Juegos llenos de adrenalina");
    }

    @Test
    void listarCategorias() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        lista.add(categoria);
        when(categoriaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Acción"))
                .andExpect(jsonPath("$[0].descripcion").value("Juegos llenos de adrenalina"));
    }

    @Test
    void noListarCategorias() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        when(categoriaService.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerCategoriaPorId() throws Exception {
        when(categoriaService.buscarPorId(1)).thenReturn(categoria);

        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Acción"))
                .andExpect(jsonPath("$.descripcion").value("Juegos llenos de adrenalina"));
    }

    @Test
    void noObtenerCategoriaPorId() throws Exception {
        when(categoriaService.buscarPorId(1)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/v1/categorias/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agregarCategoria() throws Exception {
        when(categoriaService.crear(any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(post("/api/v1/categorias")
                .contentType("application/json")
                .content("{\"id\":1,\"nombre\":\"Acción\",\"descripcion\":\"Juegos llenos de adrenalina\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Acción"))
                .andExpect(jsonPath("$.descripcion").value("Juegos llenos de adrenalina"));
    }

    @Test
    void actualizarCategoria() throws Exception {
        when(categoriaService.actualizar(eq(1), any(Categoria.class))).thenReturn(categoria);

        mockMvc.perform(put("/api/v1/categorias/1")
                .contentType("application/json")
                .content("{\"id\":1,\"nombre\":\"Acción\",\"descripcion\":\"Juegos llenos de adrenalina\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Acción"))
                .andExpect(jsonPath("$.descripcion").value("Juegos llenos de adrenalina"));
    }

    @Test
    void noActualizarCategoria() throws Exception {
        when(categoriaService.actualizar(eq(1), any(Categoria.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(put("/api/v1/categorias/1")
                .contentType("application/json")
                .content("{\"id\":1,\"nombre\":\"Acción\",\"descripcion\":\"Juegos llenos de adrenalina\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarCategoria() throws Exception {
        doNothing().when(categoriaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void noEliminarCategoria() throws Exception {
        doThrow(RuntimeException.class).when(categoriaService).eliminar(1);

        mockMvc.perform(delete("/api/v1/categorias/1"))
                .andExpect(status().isNotFound());
    }
}
