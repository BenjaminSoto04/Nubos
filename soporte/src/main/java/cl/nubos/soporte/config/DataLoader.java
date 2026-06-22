package cl.nubos.soporte.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.soporte.model.Soporte;
import cl.nubos.soporte.repository.SoporteRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(SoporteRepository soporteRepository) {

        return args -> {
            if (soporteRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            
            } else {

                Soporte soporte1 = new Soporte(null, "Error al iniciar sesión", "El usuario no puede autenticarse en la plataforma", LocalDate.parse("2026-05-25"), "Normal");
                Soporte soporte2 = new Soporte(null, "Error de Compra", "Tengo problemas para comprar un videojuego", LocalDate.parse("2026-05-25"), "Normal");

                soporteRepository.save(soporte1);
                soporteRepository.save(soporte2);

                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
