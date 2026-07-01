package cl.nubos.videojuego.config;

import cl.nubos.videojuego.model.Videojuego;
import cl.nubos.videojuego.repository.VideojuegoRepository;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(VideojuegoRepository videojuegoRepository) {

        return args -> {
            if (videojuegoRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");

            } else {

                // Se asignan IDs ficticios a desarrolladorId (1 y 2) y categoriaId (1 y 2) en
                // lugar de null,
                // cumpliendo con la restricción de que estas columnas no pueden ser nulas
                // (nullable = false).
                Videojuego videojuego1 = new Videojuego(null, "Hollow Knight", "Videojuego de Aventura y acción",
                        10000.0, LocalDate.parse("2017-02-24"), 1, 1, "E");
                Videojuego videojuego2 = new Videojuego(null, "Cuphead", "Videojuego de Plataformas y acción", 15000.0,
                        LocalDate.parse("2017-09-29"), 1, 2, "E");
                Videojuego videojuego3 = new Videojuego(null, "Undertale", "Videojuego de Rol y aventura", 8000.0,
                        LocalDate.parse("2015-09-15"), 2, 1, "E");

                videojuegoRepository.save(videojuego1);
                videojuegoRepository.save(videojuego2);
                videojuegoRepository.save(videojuego3);

                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
