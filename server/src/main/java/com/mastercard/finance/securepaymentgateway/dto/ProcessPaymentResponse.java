package com.mastercard.finance.securepaymentgateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentResponse {
    private Long txnId;
    private String transactionRef;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private double amount;
}
