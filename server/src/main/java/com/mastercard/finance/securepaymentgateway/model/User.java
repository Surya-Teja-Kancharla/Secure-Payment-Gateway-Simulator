package com.mastercard.finance.securepaymentgateway.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password_hash;
    private String cardToken;
    private Double balance;
    private String role;
}
