package com.drsimple.jwtsecurity.post;

import com.drsimple.jwtsecurity.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostsDto postsDto) {
        Posts createdPost = postService.createPost(postsDto);
        return ResponseEntity.ok(ApiResponse.success(createdPost));
    }

}
