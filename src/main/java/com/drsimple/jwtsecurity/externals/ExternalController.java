package com.drsimple.jwtsecurity.externals;


import com.drsimple.jwtsecurity.util.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external")
public class ExternalController {

    private final WebClientService webClientService;

    public ExternalController(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @GetMapping("/post")
    public void fetchPost() {
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        webClientService.get(url, PostResponse.class, null)
                .subscribe(response -> {
                    System.out.println("Fetched Post:");
                    System.out.println(response);
                });
    }

    @PostMapping("/post")
    public void sendPost() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        PostRequest requestBody = new PostRequest();
        requestBody.userId = 1;
        requestBody.title = "Hello World";
        requestBody.body = "This is a test post from WebClient.";

        webClientService.post(url, requestBody, PostResponse.class, null)
                .subscribe(response -> {
                    System.out.println("Created Post:");
                    System.out.println(response);
                });
    }



    @GetMapping("/post/client")
    public Mono<PostResponse> fetchPostClient() {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        return webClientService.get(url, PostResponse.class, null);
    }

    @PostMapping("/post/client")
    public Mono<PostResponse> sendPostClient() {
        String url = "https://jsonplaceholder.typicode.com/posts";

        PostRequest requestBody = new PostRequest();
        requestBody.userId = 1;
        requestBody.title = "Hello World";
        requestBody.body = "This is a test post from WebClient.";

        return webClientService.post(url, requestBody, PostResponse.class, null);
    }
}
