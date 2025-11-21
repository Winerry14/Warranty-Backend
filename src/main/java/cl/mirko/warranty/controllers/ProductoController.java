package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.Producto;
import cl.mirko.warranty.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @RequestParam("nombre") String nombre
    ) {
        List<Producto> productos = productoService.findByNombreProducto(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<Producto> obtenerPorSku(@PathVariable String sku) {
        return productoService.findBySku(sku)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto creado = productoService.save(producto);
        URI location = URI.create("/api/productos/" + creado.getIdProducto());
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Integer id,
            @RequestBody Producto producto
    ) {
        return productoService.findById(id)
                .map(actual -> {
                    actual.setNombreProducto(producto.getNombreProducto());
                    actual.setMarca(producto.getMarca());
                    actual.setModelo(producto.getModelo());
                    actual.setCategoria(producto.getCategoria());
                    actual.setSku(producto.getSku());
                    actual.setGarantiaMeses(producto.getGarantiaMeses());
                    Producto actualizado = productoService.save(actual);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        if (productoService.findById(id).isPresent()) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
