// ProcessPaymentRequest.java
package com.mastercard.finance.securepaymentgateway.dto;

import lombok.Data;

@Data
public class ProcessPaymentRequest {
    private Long userId;
    private Long merchantId;
    private double amount;
    private String cardToken;
}
