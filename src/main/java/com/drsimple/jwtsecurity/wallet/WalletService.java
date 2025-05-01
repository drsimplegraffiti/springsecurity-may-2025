package com.drsimple.jwtsecurity.wallet;

import com.drsimple.jwtsecurity.exception.CustomBadRequestException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import com.drsimple.jwtsecurity.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        var userExist = userRepository.findById(loggedInUser.getId());
        if (userExist.isEmpty()) throw new CustomBadRequestException("User not found");

        boolean walletExist = walletRepository.existsById(loggedInUser.getId());
        if (walletExist) {
            throw new CustomBadRequestException("Wallet already exists for this user.");
        }

        String accountNumber = generateAccountNumber();

        Wallet wallet = Wallet.builder()
                .userId(loggedInUser.getId())
                .accountNumber(accountNumber)
                .balance(0)
                .build();

        return walletRepository.save(wallet);
    }

    @Transactional
    public void transferMoney( Long toUserId, double amount) {
        User loggedInUser = currentUserUtil.getLoggedInUser();

        var userExist = userRepository.findById(loggedInUser.getId());
        if (userExist.isEmpty()) throw new CustomBadRequestException("User not found");

        var fromUserId = userExist.get().getId();

        String lockValue = UUID.randomUUID().toString(); // Unique lock value
        long lockExpiry = 5000L; // Lock expires in 5 seconds

        String fromKey = "wallet:lock:" + fromUserId;
        String toKey = "wallet:lock:" + toUserId;

        boolean fromLock = redisLockService.acquireLock(fromKey, lockValue, lockExpiry);
        boolean toLock = redisLockService.acquireLock(toKey, lockValue, lockExpiry);

        if (!fromLock || !toLock) {
            // Clean up any partial lock
            if (fromLock) redisLockService.releaseLock(fromKey, lockValue);
            if (toLock) redisLockService.releaseLock(toKey, lockValue);
            throw new IllegalStateException("Could not acquire lock for the transfer.");
        }

        try {
            Wallet fromWallet = walletRepository.findByUserId(fromUserId);
            Wallet toWallet = walletRepository.findByUserId(toUserId);

            if (fromWallet == null || toWallet == null) {
                throw new CustomBadRequestException("One or both wallets not found.");
            }

            if (fromWallet.getBalance() < amount) {
                throw new CustomBadRequestException("Insufficient funds.");
            }

            fromWallet.setBalance(fromWallet.getBalance() - amount);
            toWallet.setBalance(toWallet.getBalance() + amount);

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
