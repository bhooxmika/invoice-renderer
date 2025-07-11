package com.invoicerenderer.plugins;

import com.invoicerenderer.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogoPlugin implements InvoicePlugin {
    private static final Logger logger = LoggerFactory.getLogger(LogoPlugin.class);

    @Override
    public void apply(Invoice invoice) {
        logger.info("Setting logo for {}", invoice.getTransactionId());

        try {
            File logoFile = new File("src/main/resources/static/images/InvoiceLogo.png");
            String logoPath = logoFile.getAbsoluteFile().toURI().toString();
            invoice.setLogoUrl(logoPath);
        } catch (Exception e) {
            logger.error("Failed to load logo path", e);
        }
    }
}
