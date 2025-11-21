package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.DetalleBoleta;
import cl.mirko.warranty.models.Reclamo;
import cl.mirko.warranty.repositories.ReclamoRepository;

@Service
public class ReclamoService {

    private final ReclamoRepository reclamoRepository;

    public ReclamoService(ReclamoRepository reclamoRepository) {
        this.reclamoRepository = reclamoRepository;
    }

    public List<Reclamo> findAll() {
        return reclamoRepository.findAll();
    }

    public Optional<Reclamo> findById(Integer idReclamo) {
        return reclamoRepository.findById(idReclamo);
    }

    public List<Reclamo> findByDetalle(DetalleBoleta detalle) {
        return reclamoRepository.findByDetalle(detalle);
    }

    public Reclamo save(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public void deleteById(Integer idReclamo) {
        reclamoRepository.deleteById(idReclamo);
    }
}
