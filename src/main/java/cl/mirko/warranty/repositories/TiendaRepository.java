package cl.mirko.warranty.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    Optional<Tienda> findByRutTienda(String rutTienda);
}
