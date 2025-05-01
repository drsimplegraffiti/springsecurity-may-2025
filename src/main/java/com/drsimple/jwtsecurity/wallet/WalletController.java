package com.drsimple.jwtsecurity.wallet;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Endpoint to create a wallet
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createWallet() {
        try {
            Wallet wallet = walletService.createWallet();
            return ResponseEntity.ok(wallet);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to transfer money
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(
            @RequestParam Long toUserId,
            @RequestParam double amount) {
        try {
            walletService.transferMoney( toUserId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}