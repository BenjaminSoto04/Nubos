package cl.nubos.desarrollador.config;

import cl.nubos.desarrollador.model.Desarrollador;
import cl.nubos.desarrollador.repository.DesarrolladorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(DesarrolladorRepository desarrolladorRepository){

        return args -> {
            if (desarrolladorRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");

            } else {

                Desarrollador desarrollador1 = new Desarrollador(null, "Ari Gibson", "Team Cherry", "arigibson@gmail.com", "Australia", "gibari541");
                Desarrollador desarrollador2 = new Desarrollador(null, "Chad Moldenhauer", "Studio MDHR", "chadmolden@gmail.com", "Canadá", "haucha321");
                Desarrollador desarrollador3 = new Desarrollador(null, "Toby Fox", "Toby Fox", "tobyfox@gmail.com", "Estados Unidos", "foxtoby789");

                desarrolladorRepository.save(desarrollador1);
                desarrolladorRepository.save(desarrollador2);
                desarrolladorRepository.save(desarrollador3);
                
                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
