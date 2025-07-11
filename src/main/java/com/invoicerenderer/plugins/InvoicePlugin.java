package com.invoicerenderer.plugins;

import com.invoicerenderer.model.Invoice;

public interface InvoicePlugin {
    void apply(Invoice invoice);
}