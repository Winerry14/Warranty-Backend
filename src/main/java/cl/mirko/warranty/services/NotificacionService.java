package cl.mirko.warranty.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.mirko.warranty.models.Notificacion;
import cl.mirko.warranty.repositories.NotificacionRepository;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> findAll() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> findById(Integer idNotificacion) {
        return notificacionRepository.findById(idNotificacion);
    }

    public List<Notificacion> findByEstado(String estado) {
        return notificacionRepository.findByEstado(estado);
    }

    public Notificacion save(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public void deleteById(Integer idNotificacion) {
        notificacionRepository.deleteById(idNotificacion);
    }
}
