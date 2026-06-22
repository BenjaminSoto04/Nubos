package cl.nubos.desarrollador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.nubos.desarrollador.model.Desarrollador;

public interface DesarrolladorRepository extends JpaRepository <Desarrollador, Integer> {

}
