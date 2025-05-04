package com.drsimple.jwtsecurity.post;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final EntityManager em;
    private final ApplicationEventPublisher eventPublisher;

    public PostService(EntityManager em, ApplicationEventPublisher eventPublisher) {
        this.em = em;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Posts createPost(PostsDto postsDto) {
        Posts posts = new Posts();
        posts.setTitle(postsDto.getTitle());
        posts.setContent(postsDto.getContent());
        posts.setAuthor(postsDto.getAuthor());

        em.persist(posts);
        // Publish the event after the post is created
        eventPublisher.publishEvent(new PostsCreatedEvent(posts));
        return posts;
    }
}
