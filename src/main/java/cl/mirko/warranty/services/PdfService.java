package cl.mirko.warranty.services;

import cl.mirko.warranty.models.Boleta;
import cl.mirko.warranty.models.Garantia;
import cl.mirko.warranty.models.Producto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public ByteArrayInputStream generarPdfGarantia(Garantia garantia, Producto productoReclamado) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            Font fontImportante = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph titulo = new Paragraph("CERTIFICADO DE GARANTÍA - WARRANTY", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            Boleta boleta = garantia.getBoleta();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaFin = garantia.getFechaTermino().format(formatter);

            Paragraph estado = new Paragraph("ESTADO: " + garantia.getEstado().toUpperCase(), fontImportante);
            estado.setAlignment(Element.ALIGN_CENTER);
            document.add(estado);

            Paragraph vencimiento = new Paragraph("VENCE EL: " + fechaFin, fontTitulo);
            vencimiento.setAlignment(Element.ALIGN_CENTER);
            document.add(vencimiento);

            document.add(new Paragraph("\n--------------------------------------------------\n"));

            
            document.add(new Paragraph("DATOS DE LA COMPRA:", fontImportante));

            String nombreTienda = "No registrado";
            if (boleta.getTienda() != null && boleta.getTienda().getNombreTienda() != null) {
                nombreTienda = boleta.getTienda().getNombreTienda();
            }
            document.add(new Paragraph("Tienda: " + nombreTienda, fontNormal));

            String numeroBoleta = (boleta.getNumeroBoleta() != null ? boleta.getNumeroBoleta() : "S/N");
            document.add(new Paragraph("Boleta N°: " + numeroBoleta, fontNormal));

            document.add(new Paragraph(
                    "Fecha Compra: " + boleta.getFechaCompra().format(formatter),
                    fontNormal
            ));

            String vendedor = (boleta.getNombreVendedor() != null && !boleta.getNombreVendedor().isBlank())
                    ? boleta.getNombreVendedor()
                    : "No registrado";
            document.add(new Paragraph("Vendedor: " + vendedor, fontNormal));

            document.add(new Paragraph("\n--------------------------------------------------\n"));

            
            if (productoReclamado != null) {
                document.add(new Paragraph("PRODUCTO A RECLAMAR:", fontImportante));
                document.add(new Paragraph("Item: " + productoReclamado.getNombreProducto(), fontNormal));
                document.add(new Paragraph(
                        "Modelo: " + (productoReclamado.getModelo() != null ? productoReclamado.getModelo() : "N/A"),
                        fontNormal
                ));
                document.add(new Paragraph("\n--------------------------------------------------\n"));
            }

            
            if (boleta.getUrlImagen() != null && !boleta.getUrlImagen().isEmpty()) {
                try {
                    document.add(new Paragraph("COPIA DIGITAL DE LA BOLETA:", fontImportante));
                    document.add(new Paragraph("\n"));

                    Image imagenBoleta = Image.getInstance(boleta.getUrlImagen());
                    imagenBoleta.scaleToFit(500, 400);
                    imagenBoleta.setAlignment(Element.ALIGN_CENTER);

                    document.add(imagenBoleta);
                } catch (IOException e) {
                    document.add(new Paragraph("[No se pudo cargar la imagen de la boleta]", fontNormal));
                }
            }

            document.add(new Paragraph(
                    "\n\nDocumento generado automáticamente por Warranty.",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10)
            ));

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
