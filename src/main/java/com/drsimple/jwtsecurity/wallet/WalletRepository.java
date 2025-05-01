package com.drsimple.jwtsecurity.wallet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUserId(Long userId);
    Wallet findByAccountNumber(String accountNumber);
}