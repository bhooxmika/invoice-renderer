package com.invoicerenderer.plugins;

import com.invoicerenderer.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class OrderInfoPlugin implements InvoicePlugin {
    private static final List<String> paymentModes = Arrays.asList("UPI", "CARD", "COD", "NETBANKING");
    private static final List<String> deliveryStatuses = Arrays.asList("Delivered", "In Transit", "Pending");
    private static final Random random = new Random();

    @Override
    public void apply(Invoice invoice) {
        log.info("Populating order info for {}", invoice.getTransactionId());
        invoice.setOrderNumber("ORD-" + invoice.getTransactionId());
        invoice.setDateTime(LocalDateTime.now().toString());
        invoice.setPaymentMode(paymentModes.get(random.nextInt(paymentModes.size())));
        invoice.setDeliveryStatus(deliveryStatuses.get(random.nextInt(deliveryStatuses.size())));
        invoice.setMerchantName("Retail Pvt. Ltd.");
        invoice.setCurrency("INR");
        invoice.setCreatedAt(LocalDateTime.now().toString());
    }
}