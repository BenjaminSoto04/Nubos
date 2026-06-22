package cl.nubos.resena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.resena.model.Resena;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {

}
