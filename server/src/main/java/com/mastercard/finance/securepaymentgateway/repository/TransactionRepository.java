// TransactionRepository.java
package com.mastercard.finance.securepaymentgateway.repository;

import com.mastercard.finance.securepaymentgateway.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
