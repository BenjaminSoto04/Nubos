package cl.nubos.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.biblioteca.model.Biblioteca;

@Repository
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Integer> {

}
