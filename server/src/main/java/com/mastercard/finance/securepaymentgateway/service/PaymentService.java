package com.mastercard.finance.securepaymentgateway.service;

import com.mastercard.finance.securepaymentgateway.dto.ProcessPaymentRequest;
import com.mastercard.finance.securepaymentgateway.dto.ProcessPaymentResponse;
import com.mastercard.finance.securepaymentgateway.model.AuditLog;
import com.mastercard.finance.securepaymentgateway.model.Merchant;
import com.mastercard.finance.securepaymentgateway.model.Transaction;
import com.mastercard.finance.securepaymentgateway.model.User;
import com.mastercard.finance.securepaymentgateway.repository.AuditLogRepository;
import com.mastercard.finance.securepaymentgateway.repository.MerchantRepository;
import com.mastercard.finance.securepaymentgateway.repository.TransactionRepository;
import com.mastercard.finance.securepaymentgateway.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final UserRepository userRepo;
    private final MerchantRepository merchantRepo;
    private final TransactionRepository txnRepo;
    private final AuditLogRepository auditRepo;

    public PaymentService(UserRepository userRepo,
                          MerchantRepository merchantRepo,
                          TransactionRepository txnRepo,
                          AuditLogRepository auditRepo) {
        this.userRepo = userRepo;
        this.merchantRepo = merchantRepo;
        this.txnRepo = txnRepo;
        this.auditRepo = auditRepo;
    }

    @Transactional
    public ProcessPaymentResponse processPayment(ProcessPaymentRequest req) {
        ProcessPaymentResponse resp = new ProcessPaymentResponse();
        LocalDateTime now = LocalDateTime.now();

        User user = userRepo.findById(req.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Merchant merchant = merchantRepo.findById(req.getMerchantId()).orElseThrow(() -> new IllegalArgumentException("Merchant not found"));

        // Basic validation - card token match (simplified)
        if (req.getCardToken() == null || !req.getCardToken().equals(user.getCardToken())) {
            Transaction txn = createTransaction(user.getId(), merchant.getId(), req.getAmount(), "DECLINED", "Invalid card token", now);
            saveAudit(txn.getId(), "DECLINED: Invalid card token");
            resp.setTxnId(txn.getId());
            resp.setTransactionRef(txn.getTransactionRef());
            resp.setStatus(txn.getStatus());
            resp.setMessage(txn.getReason());
            resp.setTimestamp(now);
            return resp;
        }

        // Validation: amount > 0
        if (req.getAmount() <= 0) {
            Transaction txn = createTransaction(user.getId(), merchant.getId(), req.getAmount(), "DECLINED", "Invalid amount", now);
            saveAudit(txn.getId(), "DECLINED: Invalid amount");
            resp.setTxnId(txn.getId());
            resp.setTransactionRef(txn.getTransactionRef());
            resp.setStatus(txn.getStatus());
            resp.setMessage(txn.getReason());
            resp.setTimestamp(now);
            return resp;
        }

        // High-amount rule: decline if above threshold
        final double MAX_LIMIT = 50000.00;
        if (req.getAmount() > MAX_LIMIT) {
            Transaction txn = createTransaction(user.getId(), merchant.getId(), req.getAmount(), "DECLINED", "Amount exceeds limit", now);
            saveAudit(txn.getId(), "DECLINED: Amount exceeds limit");
            resp.setTxnId(txn.getId());
            resp.setTransactionRef(txn.getTransactionRef());
            resp.setStatus(txn.getStatus());
            resp.setMessage(txn.getReason());
            resp.setTimestamp(now);
            return resp;
        }

        // Balance check
        if (user.getBalance() < req.getAmount()) {
            Transaction txn = createTransaction(user.getId(), merchant.getId(), req.getAmount(), "DECLINED", "Insufficient funds", now);
            saveAudit(txn.getId(), "DECLINED: Insufficient funds");
            resp.setTxnId(txn.getId());
            resp.setTransactionRef(txn.getTransactionRef());
            resp.setStatus(txn.getStatus());
            resp.setMessage(txn.getReason());
            resp.setTimestamp(now);
            return resp;
        }

        // All good: perform debit/credit
        user.setBalance(roundToTwo(user.getBalance() - req.getAmount()));
        merchant.setBalance(roundToTwo(merchant.getBalance() + req.getAmount()));
        userRepo.save(user);
        merchantRepo.save(merchant);

        Transaction txn = createTransaction(user.getId(), merchant.getId(), req.getAmount(), "AUTHORIZED", null, now);
        saveAudit(txn.getId(), "AUTHORIZED");
        resp.setTxnId(txn.getId());
        resp.setTransactionRef(txn.getTransactionRef());
        resp.setStatus(txn.getStatus());
        resp.setMessage("Payment authorized");
        resp.setTimestamp(now);
        return resp;
    }

    private Transaction createTransaction(Long userId, Long merchantId, double amount, String status, String reason, LocalDateTime ts) {
        Transaction txn = new Transaction();
        txn.setTransactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8));
        txn.setUserId(userId);
        txn.setMerchantId(merchantId);
        txn.setAmount(amount);
        txn.setStatus(status);
        txn.setReason(reason);
        txn.setTimestamp(ts);
        return txnRepo.save(txn);
    }

    private void saveAudit(Long txnId, String event) {
        AuditLog log = new AuditLog();
        log.setTxnId(txnId);
        log.setEvent(event);
        log.setCreatedAt(LocalDateTime.now());
        auditRepo.save(log);
    }

    // Helper to avoid floating point issues for money in demos (production: use BigDecimal)
    private double roundToTwo(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
