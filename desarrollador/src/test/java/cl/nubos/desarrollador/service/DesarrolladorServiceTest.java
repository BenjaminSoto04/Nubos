package cl.nubos.desarrollador.service;

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

import cl.nubos.desarrollador.model.Desarrollador;
import cl.nubos.desarrollador.repository.DesarrolladorRepository;

@ExtendWith(MockitoExtension.class)
public class DesarrolladorServiceTest {

    @Mock
    private DesarrolladorRepository desarrolladorRepository;

    @InjectMocks
    private DesarrolladorService desarrolladorService;

    private Desarrollador desarrollador;

    @BeforeEach
    public void setup() {
        desarrollador = new Desarrollador();
        desarrollador.setId(1);
        desarrollador.setNombre("Juan");
        desarrollador.setEstudio("Team Cherry");
        desarrollador.setCorreo("juan@example.com");
        desarrollador.setPaís("Chile");
        desarrollador.setContraseña("password");
    }

    @Test
    void buscarDesarrolladorPorIdExistente() {
        Optional<Desarrollador> optionalDesarrollador = Optional.of(desarrollador);
        when(desarrolladorRepository.findById(1)).thenReturn(optionalDesarrollador);
        Desarrollador desarrolladorRecuperado = desarrolladorService.getDesarrolladorById(1);

        assertEquals(1, desarrolladorRecuperado.getId());
        assertEquals("Juan", desarrolladorRecuperado.getNombre());
        assertEquals("Team Cherry", desarrolladorRecuperado.getEstudio());
        assertEquals("juan@example.com", desarrolladorRecuperado.getCorreo());
        assertEquals("Chile", desarrolladorRecuperado.getPaís());
        assertEquals("password", desarrolladorRecuperado.getContraseña());
    }

    @Test
    void buscarDesarrolladorPorIdNoExistente() {
        Optional<Desarrollador> optionalDesarrollador = Optional.empty();
        when(desarrolladorRepository.findById(7)).thenReturn(optionalDesarrollador);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            desarrolladorService.getDesarrolladorById(7);
        });

        assertEquals("Desarrollador no encontrado", exception.getMessage());
    }

    @Test
    void crearDesarrollador() {
        when(desarrolladorRepository.save(desarrollador)).thenReturn(desarrollador);
        Desarrollador desarrolladorCreado = desarrolladorService.createDesarrollador(desarrollador);

        assertEquals(desarrollador, desarrolladorCreado);
        verify(desarrolladorRepository, times(1)).save(desarrollador);
    }

    @Test
    void eliminarDesarrolladorExistente() {
        when(desarrolladorRepository.existsById(1)).thenReturn(true);
        desarrolladorService.deleteDesarrollador(1);

        verify(desarrolladorRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarDesarrolladorNoExistente() {
        when(desarrolladorRepository.existsById(7)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            desarrolladorService.deleteDesarrollador(7);
        });

        assertEquals("Desarrollador no encontrado", exception.getMessage());
        verify(desarrolladorRepository, never()).deleteById(7);
    }
}
