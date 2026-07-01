package cl.nubos.soporte.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.nubos.soporte.model.Soporte;
import cl.nubos.soporte.repository.SoporteRepository;

@ExtendWith(MockitoExtension.class)
public class SoporteServiceTest {

    @Mock
    private SoporteRepository soporteRepository;

    @InjectMocks
    private SoporteService soporteService;

    private Soporte soporte;

    @BeforeEach
    public void setup() {
        soporte = new Soporte();
        soporte.setId(1);
        soporte.setTitulo("Error de Login");
        soporte.setDescripcion("No puedo iniciar sesión");
        soporte.setFechaReporte(LocalDate.of(2026, 6, 30));
        soporte.setEstado("Abierto");
    }

    @Test
    void buscarReportePorIdExistente() {
        when(soporteRepository.findById(1)).thenReturn(Optional.of(soporte));
        Soporte recuperado = soporteService.getReporteById(1);

        assertEquals(1, recuperado.getId());
        assertEquals("Error de Login", recuperado.getTitulo());
        assertEquals("No puedo iniciar sesión", recuperado.getDescripcion());
        assertEquals("Abierto", recuperado.getEstado());
    }

    @Test
    void buscarReportePorIdNoExistente() {
        when(soporteRepository.findById(7)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            soporteService.getReporteById(7);
        });

        assertEquals("Reporte no encontrado", exception.getMessage());
    }

    @Test
    void crearReporte() {
        when(soporteRepository.save(soporte)).thenReturn(soporte);
        Soporte creado = soporteService.crearReporte(soporte);

        assertEquals(soporte, creado);
        verify(soporteRepository, times(1)).save(soporte);
    }

    @Test
    void eliminarReporteExistente() {
        when(soporteRepository.existsById(1)).thenReturn(true);
        soporteService.eliminarReporte(1);

        verify(soporteRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarReporteNoExistente() {
        when(soporteRepository.existsById(7)).thenReturn(false);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            soporteService.eliminarReporte(7);
        });

        assertEquals("Reporte no encontrado", exception.getMessage());
        verify(soporteRepository, never()).deleteById(7);
    }
}
