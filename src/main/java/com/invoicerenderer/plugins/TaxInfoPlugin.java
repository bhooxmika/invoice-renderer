package com.invoicerenderer.plugins;

import com.invoicerenderer.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaxInfoPlugin implements InvoicePlugin {
    @Override
    public void apply(Invoice invoice) {
        log.info("Populating tax info for {}", invoice.getTransactionId());
        double baseAmount = invoice.getAmount();
        double taxRate = "ORIGINAL".equalsIgnoreCase(invoice.getCopyType()) ? 0.18 : 0.10;
        invoice.setAmount(baseAmount);
        invoice.setTax(baseAmount * taxRate);
    }
}