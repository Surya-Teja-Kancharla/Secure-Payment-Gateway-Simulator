package com.mastercard.finance.securepaymentgateway.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionRef;
    private Long userId;
    private Long merchantId;
    private double amount;
    private String status;
    private String reason;
    private LocalDateTime timestamp;
}
