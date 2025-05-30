package com.drsimple.jwtsecurity.blog;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private BlogRepository blogRepository;

    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    // This runs automatically after BlogService is constructed and dependencies injected
    @PostConstruct
    public void preloadSampleData() {
        if (blogRepository.count() == 0) {
            Blog sample1 = new Blog(2000L,"Getting Started with Spring Boot", "Welcome to the Spring world!", "John Doe");
            Blog sample2 = new Blog(3000L,"Why Java is awesome", "Let’s explore Java’s power.", "Jane Smith");
            blogRepository.saveAll(List.of(sample1, sample2));
            System.out.println("✅ Sample blog data loaded.");
        } else {
            System.out.println("ℹ️ Blog data already exists. Skipping preload.");
        }
    }
}
