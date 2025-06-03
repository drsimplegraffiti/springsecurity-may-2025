package com.drsimple.jwtsecurity.resilienceactivity;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequestMapping("/activity")
@RestController
public class ActivityController {

    private final RestTemplate restTemplate;

    public ActivityController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    @CircuitBreaker(
            name = "activityService",
            fallbackMethod = "getRandomActivityFallback"
    )
    public String getRandomActivity() {
        String BORED_API = "https://jsonplaceholder.typicode.com/posts/1"; // Example API endpoint
        ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(BORED_API, Activity.class);
        Activity activity = responseEntity.getBody();
        log.info("Activity received: {}", activity.getTitle());
        return activity.getBody();
    }

    public String getRandomActivityFallback(Throwable throwable){
        log.error("Error occurred while fetching activity: {}", throwable.getMessage());
        return "Fallback activity: Read a book or take a walk.";
    }
}
