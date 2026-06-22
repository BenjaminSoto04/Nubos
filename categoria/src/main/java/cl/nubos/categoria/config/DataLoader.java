package cl.nubos.categoria.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.categoria.model.Categoria;
import cl.nubos.categoria.repository.CategoriaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CategoriaRepository categoriaRepository) {
        return args -> {
            if (categoriaRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");
            } else {
                Categoria categoria1 = new Categoria(null, "Aventura", "Videojuegos de exploración y narrativa");
                Categoria categoria2 = new Categoria(null, "Acción", "Videojuegos de combate y adrenalina");
                Categoria categoria3 = new Categoria(null, "RPG", "Videojuegos de rol y desarrollo de personajes");

                categoriaRepository.save(categoria1);
                categoriaRepository.save(categoria2);
                categoriaRepository.save(categoria3);

                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
