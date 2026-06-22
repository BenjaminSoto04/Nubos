package cl.nubos.compra.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.compra.model.Compra;
import cl.nubos.compra.repository.CompraRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CompraRepository compraRepository) {
        return args -> {
            if (compraRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {
                // Se asignan IDs ficticios a usuarioId (1 y 2) y videojuegoId (1 y 2) en lugar de null,
                // cumpliendo con la restricción de que estas columnas no pueden ser nulas (nullable = false).
                Compra compra1 = new Compra(null, 1, 1, LocalDateTime.parse("2026-05-25T15:30:00"), 10000.0, "Paypal", "Completado");
                Compra compra2 = new Compra(null, 1, 2, LocalDateTime.parse("2026-05-25T15:30:00"), 16000.0, "Tarjeta de Crédito", "Completado");
                Compra compra3 = new Compra(null, 2, 1, LocalDateTime.parse("2026-05-25T15:30:00"), 8000.0, "Tarjeta de Débito", "Completado");

                compraRepository.save(compra1);
                compraRepository.save(compra2);
                compraRepository.save(compra3);
                
                System.out.println("Cargando datos de compras");
            }
        };
    }
}
