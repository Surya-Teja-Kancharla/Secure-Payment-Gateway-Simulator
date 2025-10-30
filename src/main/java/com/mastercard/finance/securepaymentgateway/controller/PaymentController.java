package com.mastercard.finance.securepaymentgateway.controller;

import com.mastercard.finance.securepaymentgateway.dto.*;
import com.mastercard.finance.securepaymentgateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

    @PostMapping("/process")
    public ResponseEntity<ProcessPaymentResponse> process(@RequestBody ProcessPaymentRequest req) {
        return ResponseEntity.ok(paymentService.processPayment(req));
    }
}
