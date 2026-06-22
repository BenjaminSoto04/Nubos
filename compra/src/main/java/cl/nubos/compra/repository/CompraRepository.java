package cl.nubos.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.compra.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

}
