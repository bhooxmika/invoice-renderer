package com.invoicerenderer.plugins;

import com.invoicerenderer.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BuyerInfoPlugin implements InvoicePlugin {
    @Override
    public void apply(Invoice invoice) {
        log.info("Populating buyer info for {}", invoice.getTransactionId());
    }
}