package cl.nubos.categoria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.nubos.categoria.model.Categoria;
import cl.nubos.categoria.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    public void setup() {
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNombre("Acción");
        categoria.setDescripcion("Juegos llenos de adrenalina");
    }

    @Test
    void buscarCategoriaPorIdExistente() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        Categoria recuperada = categoriaService.buscarPorId(1);

        assertEquals(1, recuperada.getId());
        assertEquals("Acción", recuperada.getNombre());
        assertEquals("Juegos llenos de adrenalina", recuperada.getDescripcion());
    }

    @Test
    void buscarCategoriaPorIdNoExistente() {
        when(categoriaRepository.findById(7)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoriaService.buscarPorId(7);
        });

        assertEquals("Categoria no encontrada", exception.getMessage());
    }

    @Test
    void crearCategoria() {
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria creada = categoriaService.crear(categoria);

        assertEquals(categoria, creada);
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void eliminarCategoriaExistente() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        categoriaService.eliminar(1);

        verify(categoriaRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarCategoriaNoExistente() {
        when(categoriaRepository.existsById(7)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoriaService.eliminar(7);
        });

        assertEquals("Categoria no encontrada", exception.getMessage());
        verify(categoriaRepository, never()).deleteById(7);
    }
}
