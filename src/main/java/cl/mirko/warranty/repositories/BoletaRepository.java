package cl.mirko.warranty.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.Usuario;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {

    List<Boleta> findByUsuario(Usuario usuario);

    List<Boleta> findByFechaCompraBetween(LocalDate desde, LocalDate hasta);

    List<Boleta> findByNumeroBoleta(String numeroBoleta);
}
