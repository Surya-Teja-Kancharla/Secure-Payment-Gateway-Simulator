// AuditLogRepository.java
package com.mastercard.finance.securepaymentgateway.repository;

import com.mastercard.finance.securepaymentgateway.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
