package cl.mirko.warranty.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.Garantia;
import cl.mirko.warranty.models.Notificacion;
import cl.mirko.warranty.models.Usuario;
import cl.mirko.warranty.repositories.BoletaRepository;
import cl.mirko.warranty.repositories.GarantiaRepository;
import cl.mirko.warranty.repositories.NotificacionRepository;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final GarantiaRepository garantiaRepository;
    private final NotificacionRepository notificacionRepository;

    public BoletaService(BoletaRepository boletaRepository,
                         GarantiaRepository garantiaRepository,
                         NotificacionRepository notificacionRepository) {
        this.boletaRepository = boletaRepository;
        this.garantiaRepository = garantiaRepository;
        this.notificacionRepository = notificacionRepository;
    }

    public List<Boleta> findAll() {
        return boletaRepository.findAll();
    }

    public Optional<Boleta> findById(Integer idBoleta) {
        return boletaRepository.findById(idBoleta);
    }

    public List<Boleta> findByUsuario(Usuario usuario) {
        return boletaRepository.findByUsuario(usuario);
    }

    public List<Boleta> findByRangoFecha(LocalDate desde, LocalDate hasta) {
        return boletaRepository.findByFechaCompraBetween(desde, hasta);
    }

    public List<Boleta> findByNumeroBoleta(String numeroBoleta) {
        return boletaRepository.findByNumeroBoleta(numeroBoleta);
    }

    @Transactional
    public Boleta save(Boleta boleta) {
        Boleta boletaGuardada = boletaRepository.save(boleta);
        crearGarantiaAutomatica(boletaGuardada);
        return boletaGuardada;
    }

    public void deleteById(Integer idBoleta) {
        boletaRepository.deleteById(idBoleta);
    }

    private void crearGarantiaAutomatica(Boleta boleta) {
        LocalDate fechaCompra = boleta.getFechaCompra();
        LocalDate fechaTermino = fechaCompra.plusDays(180);

        Garantia garantia = new Garantia();
        garantia.setBoleta(boleta);
        garantia.setFechaInicio(fechaCompra);
        garantia.setFechaTermino(fechaTermino);
        garantia.setEstado("VIGENTE");

        Garantia garantiaGuardada = garantiaRepository.save(garantia);
        generarNotificaciones(boleta, garantiaGuardada);
    }

    private void generarNotificaciones(Boleta boleta, Garantia garantia) {
        int[] diasAviso = {60, 30, 15, 7, 1};
        LocalDate hoy = LocalDate.now();

        for (int dias : diasAviso) {
            LocalDate fechaNotificacion = garantia.getFechaTermino().minusDays(dias);

            if (fechaNotificacion.isAfter(hoy) || fechaNotificacion.isEqual(hoy)) {
                Notificacion notificacion = new Notificacion();
                notificacion.setGarantia(garantia);
                notificacion.setFechaEnvio(fechaNotificacion.atStartOfDay());
                notificacion.setTipoNotificacion("EMAIL");
                notificacion.setEstado("PENDIENTE");
                notificacionRepository.save(notificacion);
            }
        }
    }
}
