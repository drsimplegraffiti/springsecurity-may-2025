package com.drsimple.jwtsecurity.blog;


import java.util.List;

public class BlogController {

    private BlogService blogService;

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public Blog createBlog(Blog blog) {
        return blogService.createBlog(blog);
    }

    public List<Blog> getAll() {
        return blogService.getAllBlogs();
    }
}
