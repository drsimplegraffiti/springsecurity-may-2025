package com.drsimple.jwtsecurity.resilienceactivity;

import com.drsimple.jwtsecurity.util.RestClientService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/activity")
@RestController
public class RestClientActivityController {

    private final RestClientService restClientService;

    public RestClientActivityController(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    @GetMapping("/random")
    @CircuitBreaker(name = "activityService", fallbackMethod = "getRandomActivityFallback")
    public String getRandomActivity() {
        String BORED_API = "https://jsonplaceholder.typicode.com/posts/1";

        // Call with optional token (null in this case)
        Activity activity = restClientService.get(BORED_API, Activity.class, null);

        log.info("Activity received: {}", activity.getTitle());
        return activity.getBody();
    }

    public String getRandomActivityFallback(Throwable throwable) {
        log.error("Error occurred while fetching activity: {}", throwable.getMessage());
        return "???? Fallback activity: Read a book or take a walk.";
    }
}
