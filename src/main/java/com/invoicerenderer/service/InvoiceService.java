package com.invoicerenderer.service;

import com.invoicerenderer.model.Invoice;
import com.invoicerenderer.plugins.InvoicePlugin;
import com.invoicerenderer.repo.InvoiceRepository;
import com.invoicerenderer.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PDFGenerator pdfGenerator;
    private final List<InvoicePlugin> plugins;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, PDFGenerator pdfGenerator, List<InvoicePlugin> plugins) {
        this.invoiceRepository = invoiceRepository;
        this.pdfGenerator = pdfGenerator;
        this.plugins = plugins;
    }

    public Invoice createAndRenderInvoice(Invoice invoice) {
        long startTime = System.currentTimeMillis();
        invoice.setRendered(false);
        invoiceRepository.save(invoice);

        // Apply plugins
        for (InvoicePlugin plugin : plugins) {
            plugin.apply(invoice);
        }

        String outputPath = "output/invoice-" + invoice.getTransactionId() + ".pdf";
        Path path = Paths.get(outputPath);
        if (!Files.exists(path)) {
            pdfGenerator.generatePDF(invoice, outputPath);
        } else {
            System.out.println("PDF already exists. Skipping regeneration.");
        }

        invoice.setRendered(true);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;
        System.out.println("Invoice generated for " + invoice.getTransactionId() + " in " + latency + " ms");

        return savedInvoice;
    }

    public Optional<Invoice> getInvoice(Long id) {
        return invoiceRepository.findById(id);
    }
}