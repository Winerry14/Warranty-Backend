package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.Tienda;
import cl.mirko.warranty.services.TiendaService;

@RestController
@RequestMapping("/api/tiendas")
public class TiendaController {

    private final TiendaService tiendaService;

    public TiendaController(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas() {
        List<Tienda> tiendas = tiendaService.findAll();
        return ResponseEntity.ok(tiendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tienda> obtenerTiendaPorId(@PathVariable Integer id) {
        return tiendaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tienda> crearTienda(@RequestBody Tienda tienda) {
        Tienda creada = tiendaService.save(tienda);
        URI location = URI.create("/api/tiendas/" + creada.getIdTienda());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> actualizarTienda(
            @PathVariable Integer id,
            @RequestBody Tienda tienda
    ) {
        return tiendaService.findById(id)
                .map(actual -> {
                    actual.setNombreTienda(tienda.getNombreTienda());
                    actual.setRutTienda(tienda.getRutTienda());
                    actual.setDireccion(tienda.getDireccion());
                    actual.setEmail(tienda.getEmail());
                    actual.setTelefono(tienda.getTelefono());
                    actual.setUsuario(tienda.getUsuario());
                    Tienda actualizada = tiendaService.save(actual);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTienda(@PathVariable Integer id) {
        if (tiendaService.findById(id).isPresent()) {
            tiendaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
