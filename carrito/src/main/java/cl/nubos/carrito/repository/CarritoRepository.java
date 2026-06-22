package cl.nubos.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.carrito.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

}
