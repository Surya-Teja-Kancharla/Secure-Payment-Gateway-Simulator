// ProcessPaymentResponse.java
package com.mastercard.finance.securepaymentgateway.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProcessPaymentResponse {
    private Long txnId;
    private String transactionRef;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
