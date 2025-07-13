package com.invoicerenderer.controller;

import com.invoicerenderer.model.Invoice;
import com.invoicerenderer.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired private InvoiceService service;

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(service.createAndRenderInvoice(invoice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable Long id) {
        return service.getInvoice(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pdf/{transactionId}")
    public ResponseEntity<byte[]> getInvoicePdf(@PathVariable String transactionId,
                                                @RequestParam(defaultValue = "inline") String mode) {
        try {
            byte[] pdfBytes = service.getInvoicePdf(transactionId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            ContentDisposition disposition = mode.equalsIgnoreCase("attachment")
                    ? ContentDisposition.attachment().filename(transactionId + ".pdf").build()
                    : ContentDisposition.inline().filename(transactionId + ".pdf").build();

            headers.setContentDisposition(disposition);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}