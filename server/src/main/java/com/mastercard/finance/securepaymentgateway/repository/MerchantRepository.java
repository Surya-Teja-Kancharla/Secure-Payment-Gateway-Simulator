package com.mastercard.finance.securepaymentgateway.repository;

import com.mastercard.finance.securepaymentgateway.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    // Add custom queries if needed, for example:
    // Optional<Merchant> findByName(String name);
}
