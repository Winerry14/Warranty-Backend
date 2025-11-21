package cl.mirko.warranty.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.mirko.warranty.services.OcrService;

@RestController
@RequestMapping("/api/ocr")
@CrossOrigin("*") 
public class OcrController {

    private final OcrService ocrService;

    
    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    
    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extraerTextoDeImagen(@RequestParam("file") MultipartFile file) {
        
        
        String textoEncontrado = ocrService.extraerTexto(file);

        
        Map<String, String> respuesta = new HashMap<>();
        
        if (textoEncontrado != null && !textoEncontrado.isEmpty()) {
            respuesta.put("mensaje", "Lectura exitosa");
            respuesta.put("texto", textoEncontrado);
            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.put("mensaje", "No se pudo leer texto o la imagen estaba vacía");
            respuesta.put("texto", "");
            return ResponseEntity.badRequest().body(respuesta);
        }
    }
}