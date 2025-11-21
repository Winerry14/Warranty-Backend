package cl.mirko.warranty.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.Garantia;

@Repository
public interface GarantiaRepository extends JpaRepository<Garantia, Integer> {

    Optional<Garantia> findByBoleta(Boleta boleta);
}
