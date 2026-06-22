package cl.nubos.videojuego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.videojuego.model.Videojuego;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Integer> {

}
