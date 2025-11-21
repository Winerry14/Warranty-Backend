package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.ReglaNotificacion;
import cl.mirko.warranty.repositories.ReglaNotificacionRepository;

@Service
public class ReglaNotificacionService {

    private final ReglaNotificacionRepository reglaRepository;

    public ReglaNotificacionService(ReglaNotificacionRepository reglaRepository) {
        this.reglaRepository = reglaRepository;
    }

    public List<ReglaNotificacion> findAll() {
        return reglaRepository.findAll();
    }

    public List<ReglaNotificacion> findActivas() {
        return reglaRepository.findByActivoTrue();
    }

    public Optional<ReglaNotificacion> findById(Integer idRegla) {
        return reglaRepository.findById(idRegla);
    }

    public ReglaNotificacion save(ReglaNotificacion regla) {
        return reglaRepository.save(regla);
    }

    public void deleteById(Integer idRegla) {
        reglaRepository.deleteById(idRegla);
    }
}
