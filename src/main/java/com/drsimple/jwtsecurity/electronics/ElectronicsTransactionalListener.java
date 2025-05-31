package com.drsimple.jwtsecurity.electronics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
@Slf4j
public class ElectronicsTransactionalListener {

    private final ElectronicsRepository electronicsRepository;

    // to be used when you want to handle the event after the transaction has been committed
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleElectronicsCreatedAfterCommit(ElectronicsCreatedEvent event) {
        log.info("Transactional Listener: Handling ElectronicsCreatedEvent after commit for ID {}", event.getId());

        electronicsRepository.findById(event.getId()).ifPresent(electronics -> {
            log.info("Transactional Listener: Fetched Electronics entity: {}", electronics);
            // Safe to access DB or modify here if needed
        });
    }
}