package cl.mirko.warranty.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.ReglaNotificacion;

@Repository
public interface ReglaNotificacionRepository extends JpaRepository<ReglaNotificacion, Integer> {

    List<ReglaNotificacion> findByActivoTrue();
}
