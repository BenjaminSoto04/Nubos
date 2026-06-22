package cl.nubos.usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.nubos.usuario.model.Usuario;
import cl.nubos.usuario.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository) {
        
        return args ->{
            if (usuarioRepository.count() > 0) {
                System.out.println("Los datos ya han sido cargados anteriormente");

            } else {

                Usuario usuario1 = new Usuario(null, "Juan Perez", 30, "juanperez@duocuc.cl", "aohl31");
                Usuario usuario2 = new Usuario(null, "Maria González", 28, "mariagonza@duocuc.cl", "thq512");
                Usuario usuario3 = new Usuario(null, "Carlos Rodriguez", 35, "carlosrodri@duocuc.cl", "urt618");
                
                usuarioRepository.save(usuario1);
                usuarioRepository.save(usuario2);
                usuarioRepository.save(usuario3);
                
                System.out.println("Datos cargados exitosamente");
            }
        };
    }
}
