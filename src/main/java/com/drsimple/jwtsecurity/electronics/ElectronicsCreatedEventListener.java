package com.drsimple.jwtsecurity.electronics;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ElectronicsCreatedEventListener {

    @Async
    @EventListener
    public void handleElectronicsCreatedAsync(ElectronicsCreatedEvent event) {
        log.info("Async Listener: Received ElectronicsCreatedEvent for ID {}: {}", event.getId(), event.getName());

        try {
            Thread.sleep(3000); // Simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Async Listener: Done processing event.");
    }
}
