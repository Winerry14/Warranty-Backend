package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.Reclamo;
import cl.mirko.warranty.services.DetalleBoletaService;
import cl.mirko.warranty.services.ReclamoService;

@RestController
@RequestMapping("/api/reclamos")
public class ReclamoController {

    private final ReclamoService reclamoService;
    private final DetalleBoletaService detalleBoletaService;

    public ReclamoController(ReclamoService reclamoService, DetalleBoletaService detalleBoletaService) {
        this.reclamoService = reclamoService;
        this.detalleBoletaService = detalleBoletaService;
    }

    @GetMapping
    public ResponseEntity<List<Reclamo>> listarReclamos() {
        return ResponseEntity.ok(reclamoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamo> obtenerPorId(@PathVariable Integer id) {
        return reclamoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/detalle/{idDetalle}")
    public ResponseEntity<List<Reclamo>> listarPorDetalle(@PathVariable Integer idDetalle) {
        return detalleBoletaService.findById(idDetalle)
                .map(detalle -> ResponseEntity.ok(reclamoService.findByDetalle(detalle)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reclamo> crearReclamo(@RequestBody Reclamo reclamo) {
        Reclamo creado = reclamoService.save(reclamo);
        URI location = URI.create("/api/reclamos/" + creado.getIdReclamo());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> actualizarReclamo(
            @PathVariable Integer id,
            @RequestBody Reclamo reclamo
    ) {
        return reclamoService.findById(id)
                .map(actual -> {
                    actual.setFechaReclamo(reclamo.getFechaReclamo());
                    actual.setResultado(reclamo.getResultado());
                    actual.setDetalle(reclamo.getDetalle());
                    Reclamo actualizado = reclamoService.save(actual);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReclamo(@PathVariable Integer id) {
        if (reclamoService.findById(id).isPresent()) {
            reclamoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
