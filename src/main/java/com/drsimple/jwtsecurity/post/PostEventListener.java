package com.drsimple.jwtsecurity.post;

import org.springframework.stereotype.Component;

@Component
public class PostEventListener {
    // This method will be called when a PostsCreatedEvent is published.
    @org.springframework.context.event.EventListener
    public void handlePostsCreatedEvent(PostsCreatedEvent event) {
        Posts posts = event.getPosts();
        System.out.println("New post created: " + posts.getTitle());
        // You can add more logic here, like sending notifications or updating caches.
    }
}
