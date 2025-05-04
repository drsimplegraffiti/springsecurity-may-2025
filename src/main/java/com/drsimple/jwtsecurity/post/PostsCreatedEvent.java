package com.drsimple.jwtsecurity.post;

import org.springframework.context.ApplicationEvent;

public class PostsCreatedEvent extends ApplicationEvent {
    public PostsCreatedEvent(Posts posts) {
        super(posts);
    }

    // This method returns the source of the event, which is the Posts object.
    public Posts getPosts() {
        return (Posts) getSource();
    }
}
