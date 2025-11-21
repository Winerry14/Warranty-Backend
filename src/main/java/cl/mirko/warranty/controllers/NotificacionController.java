package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.Notificacion;
import cl.mirko.warranty.services.NotificacionService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public ResponseEntity<List<Notificacion>> listarNotificaciones() {
        return ResponseEntity.ok(notificacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerPorId(@PathVariable Integer id) {
        return notificacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> listarPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(notificacionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion creada = notificacionService.save(notificacion);
        URI location = URI.create("/api/notificaciones/" + creada.getIdNotificacion());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizarNotificacion(
            @PathVariable Integer id,
            @RequestBody Notificacion notificacion
    ) {
        return notificacionService.findById(id)
                .map(actual -> {
                    actual.setFechaEnvio(notificacion.getFechaEnvio());
                    actual.setTipoNotificacion(notificacion.getTipoNotificacion());
                    actual.setEstado(notificacion.getEstado());
                    actual.setBoleta(notificacion.getBoleta());
                    actual.setGarantia(notificacion.getGarantia());
                    actual.setReglaNotificacion(notificacion.getReglaNotificacion());
                    Notificacion actualizada = notificacionService.save(actual);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Integer id) {
        if (notificacionService.findById(id).isPresent()) {
            notificacionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
