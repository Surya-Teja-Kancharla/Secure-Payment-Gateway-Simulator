package com.mastercard.finance.securepaymentgateway.controller;

import com.mastercard.finance.securepaymentgateway.model.*;
import com.mastercard.finance.securepaymentgateway.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserRepository userRepo;
    private final MerchantRepository merchantRepo;
    private final TransactionRepository txnRepo;
    private final AuditLogRepository auditRepo;

    public AdminController(UserRepository userRepo, MerchantRepository merchantRepo,
                           TransactionRepository txnRepo, AuditLogRepository auditRepo) {
        this.userRepo = userRepo;
        this.merchantRepo = merchantRepo;
        this.txnRepo = txnRepo;
        this.auditRepo = auditRepo;
    }

    @GetMapping("/users")
    public List<User> users() { return userRepo.findAll(); }

    @GetMapping("/merchants")
    public List<Merchant> merchants() { return merchantRepo.findAll(); }

    @GetMapping("/transactions")
    public List<Transaction> transactions() { return txnRepo.findAll(); }

    @GetMapping("/audits")
    public List<AuditLog> audits() { return auditRepo.findAll(); }
}
