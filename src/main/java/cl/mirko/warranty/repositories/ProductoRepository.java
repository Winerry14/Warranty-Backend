package cl.mirko.warranty.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mirko.warranty.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findBySku(String sku);

    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);
}
