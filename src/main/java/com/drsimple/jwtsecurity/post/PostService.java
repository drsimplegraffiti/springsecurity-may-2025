package com.drsimple.jwtsecurity.post;


import com.drsimple.jwtsecurity.product.Product;
import com.drsimple.jwtsecurity.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final EntityManager em;
    private final ApplicationEventPublisher eventPublisher;
    private final ProductRepository productRepository;
    private final PostsRepository postsRepository;

    public PostService(EntityManager em, ApplicationEventPublisher eventPublisher, ProductRepository productRepository,
                       PostsRepository postsRepository) {
        this.em = em;
        this.eventPublisher = eventPublisher;
        this.productRepository = productRepository;
        this.postsRepository = postsRepository;
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

    public Product getPostById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }
}
