package cl.mirko.warranty.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.DetalleBoleta;
import cl.mirko.warranty.models.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Integer> {

    List<Reclamo> findByDetalle(DetalleBoleta detalle);
}
