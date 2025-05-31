package com.drsimple.jwtsecurity.electronics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ElectronicsCreatedEvent extends ApplicationEvent {

    private final Long id;
    private final String name;

    public ElectronicsCreatedEvent(Object source, Long id, String name) {
        super(source);
        this.id = id;
        this.name = name;
    }
}