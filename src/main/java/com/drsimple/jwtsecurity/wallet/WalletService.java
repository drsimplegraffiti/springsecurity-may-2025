package com.drsimple.jwtsecurity.wallet;

import com.drsimple.jwtsecurity.exception.CustomBadRequestException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final RedisLockService redisLockService;
    private final UserRepository userRepository;
    private final CurrentUserUtil currentUserUtil;


    @Autowired
    public WalletService(WalletRepository walletRepository, RedisLockService redisLockService, UserRepository userRepository, CurrentUserUtil currentUserUtil) {
        this.walletRepository = walletRepository;
        this.redisLockService = redisLockService;
        this.userRepository = userRepository;
        this.currentUserUtil = currentUserUtil;
    }

    @Transactional
    public Wallet createWallet() {
        User loggedInUser = currentUserUtil.getLoggedInUser();

        User user = userRepository.findById(loggedInUser.getId())
                .orElseThrow(() -> new CustomBadRequestException("User not found"));

        if (walletRepository.existsById(loggedInUser.getId())) {
            throw new CustomBadRequestException("Wallet already exists for this user.");
        }

        String accountNumber = generateAccountNumber();

        Wallet wallet = Wallet.builder()
                .userId(loggedInUser.getId())
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .build();

        user.setAccountNumber(accountNumber);
        userRepository.save(user);

        return walletRepository.save(wallet);
    }

    @Transactional
    public void transferMoney( String accountNumber, BigDecimal amount) {
        User loggedInUser = currentUserUtil.getLoggedInUser();

        var userExist = userRepository.findById(loggedInUser.getId());
        if (userExist.isEmpty()) throw new CustomBadRequestException("User not found");

        var fromUserId = userExist.get().getId();

        String lockValue = UUID.randomUUID().toString(); // Unique lock value
        long lockExpiry = 5000L; // Lock expires in 5 seconds

        String fromKey = "wallet:lock:" + userExist.get().getAccountNumber();
        String toKey = "wallet:lock:" + accountNumber;

        boolean fromLock = redisLockService.acquireLock(fromKey, lockValue, lockExpiry);
        boolean toLock = redisLockService.acquireLock(toKey, lockValue, lockExpiry);

        if (!fromLock || !toLock) {
            // Clean up any partial lock
            if (fromLock) redisLockService.releaseLock(fromKey, lockValue);
            if (toLock) redisLockService.releaseLock(toKey, lockValue);
            throw new IllegalStateException("Could not acquire lock for the transfer.");
        }

        try {
            Wallet fromWallet = walletRepository.findByAccountNumber(accountNumber);
            Wallet toWallet = walletRepository.findByAccountNumber(userExist.get().getAccountNumber());

            if (fromWallet == null || toWallet == null) {
                throw new CustomBadRequestException("One or both wallets not found.");
            }

            if (fromWallet.getBalance().compareTo(amount) < 0) {
                throw new CustomBadRequestException("Insufficient funds.");
            }

            fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
            toWallet.setBalance(toWallet.getBalance().add(amount));

            walletRepository.save(fromWallet);
            walletRepository.save(toWallet);
        } finally {
            redisLockService.releaseLock(fromKey, lockValue);
            redisLockService.releaseLock(toKey, lockValue);
        }
    }

    private String generateAccountNumber() {
        // Example: Random 10-digit number prefixed with '10'
        return "10" + String.format("%010d", (long)(Math.random() * 1_000_000_0000L));
    }
}
