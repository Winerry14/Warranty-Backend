package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.DetalleBoleta;
import cl.mirko.warranty.repositories.DetalleBoletaRepository;

@Service
public class DetalleBoletaService {

    private final DetalleBoletaRepository detalleBoletaRepository;

    public DetalleBoletaService(DetalleBoletaRepository detalleBoletaRepository) {
        this.detalleBoletaRepository = detalleBoletaRepository;
    }

    public List<DetalleBoleta> findAll() {
        return detalleBoletaRepository.findAll();
    }

    public Optional<DetalleBoleta> findById(Integer idDetalle) {
        return detalleBoletaRepository.findById(idDetalle);
    }

    public List<DetalleBoleta> findByBoleta(Boleta boleta) {
        return detalleBoletaRepository.findByBoleta(boleta);
    }

    public DetalleBoleta save(DetalleBoleta detalle) {
        return detalleBoletaRepository.save(detalle);
    }

    public void deleteById(Integer idDetalle) {
        detalleBoletaRepository.deleteById(idDetalle);
    }
}
