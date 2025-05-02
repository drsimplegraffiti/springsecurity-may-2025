package com.drsimple.jwtsecurity.blog;

import java.util.List;

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
}
