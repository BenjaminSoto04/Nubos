package cl.nubos.carrito.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.carrito.model.Carrito;
import cl.nubos.carrito.repository.CarritoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CarritoRepository carritoRepository) {
        return args -> {
            if (carritoRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {
                
                // Se asignan IDs ficticios a usuarioId (1 y 2) y videojuegoId (1 y 2) en lugar de null,
                // cumpliendo con la restricción de que estas columnas no pueden ser nulas (nullable = false).
                Carrito carrito1 = new Carrito(null, 1, 1, 2, LocalDateTime.parse("2026-05-25T15:30:00"), 40000.0);
                Carrito carrito2 = new Carrito(null, 1, 2, 1, LocalDateTime.parse("2026-05-25T15:30:00"), 20000.0);
                Carrito carrito3 = new Carrito(null, 2, 1, 3, LocalDateTime.parse("2026-05-25T15:30:00"), 60000.0);

                carritoRepository.save(carrito1);
                carritoRepository.save(carrito2);
                carritoRepository.save(carrito3);

                System.out.println("Cargando datos de carrito");
            }
        };
    }
}
