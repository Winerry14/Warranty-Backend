package cl.mirko.warranty.controllers;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.services.BoletaService;
import cl.mirko.warranty.services.UsuarioService;

@RestController
@RequestMapping("/api/boletas")
@CrossOrigin("*")
public class BoletaController {

    private final BoletaService boletaService;
    private final UsuarioService usuarioService;

    public BoletaController(BoletaService boletaService, UsuarioService usuarioService) {
        this.boletaService = boletaService;
        this.usuarioService = usuarioService;
    }

    

    @GetMapping
    public ResponseEntity<List<Boleta>> listarBoletas() {
        return ResponseEntity.ok(boletaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleta> obtenerPorId(@PathVariable Integer id) {
        return boletaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Boleta>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return usuarioService.findById(idUsuario)
                .map(usuario -> ResponseEntity.ok(boletaService.findByUsuario(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/rango-fecha")
    public ResponseEntity<List<Boleta>> listarPorRangoFecha(
            @RequestParam("desde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam("hasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return ResponseEntity.ok(boletaService.findByRangoFecha(desde, hasta));
    }

    @GetMapping("/numero/{numeroBoleta}")
    public ResponseEntity<List<Boleta>> listarPorNumero(@PathVariable String numeroBoleta) {
        return ResponseEntity.ok(boletaService.findByNumeroBoleta(numeroBoleta));
    }

    

    @PostMapping
    public ResponseEntity<Boleta> crearBoleta(@RequestBody Boleta boleta) {
        
        Boleta creada = boletaService.save(boleta);
        URI location = URI.create("/api/boletas/" + creada.getIdBoleta());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizarBoleta(
            @PathVariable Integer id,
            @RequestBody Boleta boleta
    ) {
        return boletaService.findById(id)
                .map(actual -> {
                    actual.setNumeroBoleta(boleta.getNumeroBoleta());
                    actual.setFechaCompra(boleta.getFechaCompra());
                    actual.setTotal(boleta.getTotal());
                    actual.setUrlImagen(boleta.getUrlImagen());
                    actual.setNombreVendedor(boleta.getNombreVendedor());
                    actual.setUsuario(boleta.getUsuario());
                    actual.setTienda(boleta.getTienda());
                    Boleta actualizada = boletaService.save(actual);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleta(@PathVariable Integer id) {
        if (boletaService.findById(id).isPresent()) {
            boletaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> subirArchivo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String uploadsDir = "uploads/boletas";
            Path uploadsPath = Paths.get(uploadsDir);
            Files.createDirectories(uploadsPath);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path destino = uploadsPath.resolve(fileName);

            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            String ruta = uploadsDir + "/" + fileName;

            Map<String, String> body = new HashMap<>();
            body.put("ruta", ruta);

            return ResponseEntity.ok(body);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    

    @GetMapping("/{id}/archivo")
    public ResponseEntity<Resource> verArchivo(@PathVariable Integer id) {
        var optionalBoleta = boletaService.findById(id);
        if (optionalBoleta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Boleta boleta = optionalBoleta.get();
        String ruta = boleta.getUrlImagen();
        if (ruta == null || ruta.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path path = Paths.get(ruta);
            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource recurso = new UrlResource(path.toUri());

            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(recurso);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
