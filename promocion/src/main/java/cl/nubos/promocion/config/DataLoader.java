package cl.nubos.promocion.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.promocion.model.Promocion;
import cl.nubos.promocion.repository.PromocionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(PromocionRepository promocionRepository) {
        return args -> {
            if (promocionRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {

                // Se asigna un ID ficticio a videojuegoId (1 y 2) en lugar de null,
                // cumpliendo con la restricción de que esta columna no puede ser nula (nullable = false).
                Promocion promocion1 = new Promocion(null, 1, 30, LocalDate.parse("2026-05-25"), LocalDate.parse("2026-06-25"), "INVIERNO30", true);
                Promocion promocion2 = new Promocion(null, 2, 20, LocalDate.parse("2026-05-25"), LocalDate.parse("2026-06-25"), "NUBLADO20", true);

                promocionRepository.save(promocion1);
                promocionRepository.save(promocion2);

                System.out.println("Cargando datos de promoción");
            }
        };

    }
}
