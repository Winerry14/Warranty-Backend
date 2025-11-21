package cl.mirko.warranty.services;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class OcrService {

    public String extraerTexto(MultipartFile file) {
        try {
            
            File convFile = convert(file);

            
            Tesseract tesseract = new Tesseract();

            
            tesseract.setDatapath("C:/tessdata"); 
            tesseract.setLanguage("spa");

            
            String texto = tesseract.doOCR(convFile);

            
            convFile.delete();

            return texto;

        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return "Error al procesar la imagen: " + e.getMessage();
        }
    }

    
    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        
        
        return convFile; 
    }
}