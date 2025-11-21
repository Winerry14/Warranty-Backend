package cl.mirko.warranty.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.DetalleBoleta;

@Repository
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {

    List<DetalleBoleta> findByBoleta(Boleta boleta);
}
