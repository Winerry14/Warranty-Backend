package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.ReglaNotificacion;
import cl.mirko.warranty.services.ReglaNotificacionService;

@RestController
@RequestMapping("/api/reglas-notificacion")
public class ReglaNotificacionController {

    private final ReglaNotificacionService reglaService;

    public ReglaNotificacionController(ReglaNotificacionService reglaService) {
        this.reglaService = reglaService;
    }

    @GetMapping
    public ResponseEntity<List<ReglaNotificacion>> listarReglas() {
        return ResponseEntity.ok(reglaService.findAll());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<ReglaNotificacion>> listarReglasActivas() {
        return ResponseEntity.ok(reglaService.findActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReglaNotificacion> obtenerPorId(@PathVariable Integer id) {
        return reglaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReglaNotificacion> crearRegla(@RequestBody ReglaNotificacion regla) {
        ReglaNotificacion creada = reglaService.save(regla);
        URI location = URI.create("/api/reglas-notificacion/" + creada.getIdRegla());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReglaNotificacion> actualizarRegla(
            @PathVariable Integer id,
            @RequestBody ReglaNotificacion regla
    ) {
        return reglaService.findById(id)
                .map(actual -> {
                    actual.setNombreRegla(regla.getNombreRegla());
                    actual.setDiasPrevistos(regla.getDiasPrevistos());
                    actual.setCanal(regla.getCanal());
                    actual.setPlantilla(regla.getPlantilla());
                    actual.setActivo(regla.getActivo());
                    actual.setUsuario(regla.getUsuario());
                    ReglaNotificacion actualizada = reglaService.save(actual);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegla(@PathVariable Integer id) {
        if (reglaService.findById(id).isPresent()) {
            reglaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
