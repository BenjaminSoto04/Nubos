package cl.nubos.promocion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.nubos.promocion.model.Promocion;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Integer> {

}
