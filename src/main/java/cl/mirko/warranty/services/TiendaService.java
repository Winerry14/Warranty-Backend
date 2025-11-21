package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.Tienda;
import cl.mirko.warranty.repositories.TiendaRepository;

@Service
public class TiendaService {

    private final TiendaRepository tiendaRepository;

    public TiendaService(TiendaRepository tiendaRepository) {
        this.tiendaRepository = tiendaRepository;
    }

    public List<Tienda> findAll() {
        return tiendaRepository.findAll();
    }

    public Optional<Tienda> findById(Integer idTienda) {
        return tiendaRepository.findById(idTienda);
    }

    public Optional<Tienda> findByRutTienda(String rutTienda) {
        return tiendaRepository.findByRutTienda(rutTienda);
    }

    public Tienda save(Tienda tienda) {
        return tiendaRepository.save(tienda);
    }

    public void deleteById(Integer idTienda) {
        tiendaRepository.deleteById(idTienda);
    }
}
