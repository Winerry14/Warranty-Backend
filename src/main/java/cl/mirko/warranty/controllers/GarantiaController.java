package cl.mirko.warranty.controllers;

import java.net.URI;
import java.util.List;
import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.mirko.warranty.models.Garantia;
import cl.mirko.warranty.services.BoletaService;
import cl.mirko.warranty.services.GarantiaService;
import cl.mirko.warranty.services.PdfService;

@RestController
@RequestMapping("/api/garantias")
@CrossOrigin("*") 
public class GarantiaController {

    private final GarantiaService garantiaService;
    private final BoletaService boletaService;
    private final PdfService pdfService; 

    
    public GarantiaController(GarantiaService garantiaService, 
                              BoletaService boletaService, 
                              PdfService pdfService) {
        this.garantiaService = garantiaService;
        this.boletaService = boletaService;
        this.pdfService = pdfService;
    }

    

    @GetMapping
    public ResponseEntity<List<Garantia>> listarGarantias() {
        return ResponseEntity.ok(garantiaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garantia> obtenerPorId(@PathVariable Integer id) {
        return garantiaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<Garantia> obtenerPorBoleta(@PathVariable Integer idBoleta) {
        return boletaService.findById(idBoleta)
                .flatMap(boleta -> garantiaService.findByBoleta(boleta))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> descargarCertificado(@PathVariable Integer id) {
        
        Garantia garantia = garantiaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Garantía no encontrada con ID: " + id));

        
        ByteArrayInputStream pdfStream = pdfService.generarPdfGarantia(garantia, null);

        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=certificado_garantia_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    

    @PostMapping
    public ResponseEntity<Garantia> crearGarantia(@RequestBody Garantia garantia) {
        Garantia creada = garantiaService.save(garantia);
        URI location = URI.create("/api/garantias/" + creada.getIdGarantia());
        return ResponseEntity.created(location).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garantia> actualizarGarantia(
            @PathVariable Integer id,
            @RequestBody Garantia garantia
    ) {
        return garantiaService.findById(id)
                .map(actual -> {
                    actual.setFechaInicio(garantia.getFechaInicio());
                    actual.setFechaTermino(garantia.getFechaTermino());
                    actual.setEstado(garantia.getEstado());
                    actual.setBoleta(garantia.getBoleta());
                    Garantia actualizada = garantiaService.save(actual);
                    return ResponseEntity.ok(actualizada);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGarantia(@PathVariable Integer id) {
        if (garantiaService.findById(id).isPresent()) {
            garantiaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}