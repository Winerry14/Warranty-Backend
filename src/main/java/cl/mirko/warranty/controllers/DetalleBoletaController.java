package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.DetalleBoleta;
import cl.mirko.warranty.services.BoletaService;
import cl.mirko.warranty.services.DetalleBoletaService;

@RestController
@RequestMapping("/api/detalles-boleta")
public class DetalleBoletaController {

    private final DetalleBoletaService detalleBoletaService;
    private final BoletaService boletaService;

    public DetalleBoletaController(DetalleBoletaService detalleBoletaService, BoletaService boletaService) {
        this.detalleBoletaService = detalleBoletaService;
        this.boletaService = boletaService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleBoleta>> listarDetalles() {
        return ResponseEntity.ok(detalleBoletaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> obtenerPorId(@PathVariable Integer id) {
        return detalleBoletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<List<DetalleBoleta>> listarPorBoleta(@PathVariable Integer idBoleta) {
        return boletaService.findById(idBoleta)
                .map(boleta -> ResponseEntity.ok(detalleBoletaService.findByBoleta(boleta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DetalleBoleta> crearDetalle(@RequestBody DetalleBoleta detalle) {
        DetalleBoleta creado = detalleBoletaService.save(detalle);
        URI location = URI.create("/api/detalles-boleta/" + creado.getIdDetalle());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> actualizarDetalle(
            @PathVariable Integer id,
            @RequestBody DetalleBoleta detalle
    ) {
        return detalleBoletaService.findById(id)
                .map(actual -> {
                    actual.setBoleta(detalle.getBoleta());
                    actual.setProducto(detalle.getProducto());
                    actual.setCantidad(detalle.getCantidad());
                    actual.setPrecioUnitario(detalle.getPrecioUnitario());
                    DetalleBoleta actualizado = detalleBoletaService.save(actual);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        if (detalleBoletaService.findById(id).isPresent()) {
            detalleBoletaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
