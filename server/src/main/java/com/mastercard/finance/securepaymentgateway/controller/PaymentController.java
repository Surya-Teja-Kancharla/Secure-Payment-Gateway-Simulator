package com.mastercard.finance.securepaymentgateway.controller;

import com.mastercard.finance.securepaymentgateway.dto.ProcessPaymentRequest;
import com.mastercard.finance.securepaymentgateway.dto.ProcessPaymentResponse;
import com.mastercard.finance.securepaymentgateway.model.Transaction;
import com.mastercard.finance.securepaymentgateway.repository.TransactionRepository;
import com.mastercard.finance.securepaymentgateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // ✅ Allow React frontend access
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final TransactionRepository transactionRepository;

    public PaymentController(PaymentService paymentService, TransactionRepository transactionRepository) {
        this.paymentService = paymentService;
        this.transactionRepository = transactionRepository;
    }

    /**
     * 💳 Process a new payment request
     * URL: POST /api/payments/process
     * Body: { "userId":1, "merchantId":1, "amount":1500, "cardToken":"TOK-1111-2222" }
     */
    @PostMapping("/process")
    public ResponseEntity<ProcessPaymentResponse> process(@RequestBody ProcessPaymentRequest req) {
        return ResponseEntity.ok(paymentService.processPayment(req));
    }

    /**
     * 📜 Fetch all transactions
     * URL: GET /api/payments/transactions/all
     * Used by the React frontend to display the transactions table
     */
    @GetMapping("/transactions/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return ResponseEntity.ok(transactions);
    }
}
