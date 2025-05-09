package com.drsimple.jwtsecurity.post;

import com.drsimple.jwtsecurity.product.Product;
import com.drsimple.jwtsecurity.util.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@Tag(name = "Post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping // Handles HTTP POST requests to /posts
    public ResponseEntity<?> createPost(@RequestBody PostsDto postsDto) {
        Posts createdPost = postService.createPost(postsDto);

        // Wrap the created post in an EntityModel to add HATEOAS links
        EntityModel<Posts> postResource = EntityModel.of(
                createdPost,
                // Self link pointing back to this createPost endpoint (not very useful for POST, but added for HATEOAS compliance)
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).createPost(postsDto)).withSelfRel(),

                // Link to a GET endpoint for retrieving this specific post by its ID
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(createdPost.getId())).withRel("get-post")
        );

        return ResponseEntity.ok(ApiResponse.success(postResource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Product post = postService.getPostById(id);

        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();

        // Convert each post to an EntityModel with links
        List<EntityModel<Posts>> postResources = posts.stream()
                .map(post -> EntityModel.of(
                        post,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getPostById(post.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts()).withRel("all-posts")
                ))
                .collect(Collectors.toList());

        // Wrap the list in a CollectionModel and add a self link
        CollectionModel<EntityModel<Posts>> collectionModel = CollectionModel.of(
                postResources,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAllPosts()).withSelfRel()
        );

        return ResponseEntity.ok(ApiResponse.success(collectionModel));
    }
}
