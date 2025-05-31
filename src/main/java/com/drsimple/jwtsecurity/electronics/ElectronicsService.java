package com.drsimple.jwtsecurity.electronics;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ElectronicsService {

    private final ElectronicsRepository electronicsRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Electronics createElectronics(Electronics electronics) {
        Electronics saved = electronicsRepository.save(electronics);

        // Publish event AFTER commit
        eventPublisher.publishEvent(new ElectronicsCreatedEvent(this, saved.getId(), saved.getName()));
        return saved;
    }
}