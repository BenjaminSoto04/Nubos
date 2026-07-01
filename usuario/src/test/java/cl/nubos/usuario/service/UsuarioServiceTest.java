package cl.nubos.usuario.service;

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

import cl.nubos.usuario.model.Usuario;
import cl.nubos.usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setEdad(25);
        usuario.setCorreo("juan@example.com");
        usuario.setContraseña("password");
    }

    @Test
    void buscarUsuarioPorIdExistente() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Usuario recuperado = usuarioService.buscarUsuarioPorId(1);

        assertEquals(1, recuperado.getId());
        assertEquals("Juan", recuperado.getNombre());
        assertEquals(25, recuperado.getEdad());
        assertEquals("juan@example.com", recuperado.getCorreo());
        assertEquals("password", recuperado.getContraseña());
    }

    @Test
    void buscarUsuarioPorIdNoExistente() {
        when(usuarioRepository.findById(7)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(7);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void crearUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario creado = usuarioService.crearUsuario(usuario);

        assertEquals(usuario, creado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void eliminarUsuarioExistente() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        usuarioService.eliminarUsuario(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarUsuarioNoExistente() {
        when(usuarioRepository.existsById(7)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.eliminarUsuario(7);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, never()).deleteById(7);
    }
}
