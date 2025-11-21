package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.Garantia;
import cl.mirko.warranty.repositories.GarantiaRepository;

@Service
public class GarantiaService {

    private final GarantiaRepository garantiaRepository;

    public GarantiaService(GarantiaRepository garantiaRepository) {
        this.garantiaRepository = garantiaRepository;
    }

    public List<Garantia> findAll() {
        return garantiaRepository.findAll();
    }

    public Optional<Garantia> findById(Integer idGarantia) {
        return garantiaRepository.findById(idGarantia);
    }

    public Optional<Garantia> findByBoleta(Boleta boleta) {
        return garantiaRepository.findByBoleta(boleta);
    }

    public Garantia save(Garantia garantia) {
        return garantiaRepository.save(garantia);
    }

    public void deleteById(Integer idGarantia) {
        garantiaRepository.deleteById(idGarantia);
    }
}
