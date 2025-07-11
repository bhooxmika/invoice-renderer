package com.invoicerenderer.util;

import com.invoicerenderer.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class PDFGenerator {

    private final TemplateEngine templateEngine;

    public PDFGenerator(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generatePDF(Invoice invoice, String filePath) {
        try {
            Files.createDirectories(Paths.get(filePath).getParent());
            try (OutputStream os = new FileOutputStream(filePath)) {
                Context context = new Context();
                context.setVariable("invoice", invoice);
                String htmlContent = templateEngine.process("invoice", context);

                ITextRenderer renderer = new ITextRenderer();
                renderer.setDocumentFromString(htmlContent);
                renderer.layout();
                renderer.createPDF(os);
                log.info("PDF successfully generated at: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Error generating PDF at {}: {}", filePath, e.getMessage(), e);
        }
    }
}