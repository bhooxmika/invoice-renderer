package com.invoicerenderer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import lombok.Data;

@Data
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String copyType;

    @Column(nullable = false)
    private boolean rendered;

    private String orderNumber;
    private String dateTime;
    private Double amount;
    private Double tax;
    private String paymentMode;
    private String deliveryStatus;
    private String merchantName;
    private String currency;
    private String createdAt;
    private String logoUrl;
}
