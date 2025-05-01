package com.drsimple.jwtsecurity.wallet;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double balance;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 12)
    private String accountNumber;

}