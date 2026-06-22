package cl.nubos.biblioteca.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.biblioteca.model.Biblioteca;
import cl.nubos.biblioteca.repository.BibliotecaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner inintData(BibliotecaRepository bibliotecaRepository) {
        return args -> {
            if (bibliotecaRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {

                // Se asignan IDs ficticios a usuarioId (1) y videojuegoId (1 y 2) en lugar de null,
                // cumpliendo con la restricción de que estas columnas no pueden ser nulas (nullable = false).
                Biblioteca biblioteca1 = new Biblioteca(null, 1, 1, LocalDate.parse("2026-05-25"), 100, LocalDateTime.parse("2026-05-25T15:30:00"));
                Biblioteca biblioteca2 = new Biblioteca(null, 1, 2, LocalDate.parse("2026-05-25"), 150, LocalDateTime.parse("2026-05-25T15:30:00"));

                bibliotecaRepository.save(biblioteca1);
                bibliotecaRepository.save(biblioteca2);

                System.out.println("Cargando datos de biblioteca");
            }
        };
    }
}
