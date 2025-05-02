package com.drsimple.jwtsecurity.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogRestController {

    private final BlogController blogController;

    @Autowired
    public BlogRestController(BlogController blogController) {
        this.blogController = blogController;
    }

    @PostMapping
    public Blog createBlog(@RequestBody Blog blog) {
        return blogController.createBlog(blog);
    }

    @GetMapping
    public List<Blog> getAll() {
        return blogController.getAll();
    }
}
