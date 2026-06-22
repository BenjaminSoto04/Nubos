package cl.nubos.resena.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.resena.model.Resena;
import cl.nubos.resena.repository.ResenaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ResenaRepository resenaRepository) {
        return args -> {
            if (resenaRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {
                // Se asignan IDs ficticios a usuarioId (1 y 2) y videojuegoId (1 y 2) en lugar de null,
                // cumpliendo con la restricción de que estas columnas no pueden ser nulas (nullable = false).
                Resena resena1 = new Resena(null, 1, 1, 5, "Excelente juego con una historia envolvente y gráficos impresionantes.", LocalDate.parse("2026-05-25"), true);
                Resena resena2 = new Resena(null, 1, 2, 4, "Muy divertido y adictivo, aunque tiene algunos bugs menores.", LocalDate.parse("2026-05-25"), true);
                Resena resena3 = new Resena(null, 2, 1, 2, "No cumplió mis expectativas, la jugabilidad es repetitiva.", LocalDate.parse("2026-05-25"), false);

                resenaRepository.save(resena1);
                resenaRepository.save(resena2);
                resenaRepository.save(resena3);

                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
