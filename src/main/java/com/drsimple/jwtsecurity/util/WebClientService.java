package com.drsimple.jwtsecurity.util;

//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Service
//public class WebClientService {
//
//    private final WebClient webClient = WebClient.builder()
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .build();
//
//    // GET request with optional headers
//    public <T> Mono<T> get(String url, Class<T> responseType, String authToken) {
//        WebClient.RequestBodySpec request = (WebClient.RequestBodySpec) webClient.get().uri(url);
//        if (authToken != null && !authToken.isEmpty()) {
//            request = request.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken));
//        }
//
//        return request.retrieve()
//                .bodyToMono(responseType);
//    }
//
//    // POST request with optional headers and body
//    public <T, R> Mono<R> post(String url, T requestBody, Class<R> responseType, String authToken) {
//        WebClient.RequestBodySpec request = (WebClient.RequestBodySpec) webClient.post().uri(url).bodyValue(requestBody);
//        if (authToken != null && !authToken.isEmpty()) {
//            request = request.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken));
//        }
//
//        return request.retrieve()
//                .bodyToMono(responseType);
//    }
//}


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @CircuitBreaker(name = "webClientCB", fallbackMethod = "fallback")
    public <T> Mono<T> get(String url, Class<T> responseType, String authToken) {
        WebClient.RequestBodySpec request = (WebClient.RequestBodySpec) webClient.get().uri(url);
        if (authToken != null && !authToken.isEmpty()) {
            request = request.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken));
        }

        return request.retrieve()
                .bodyToMono(responseType);
    }

    @CircuitBreaker(name = "webClientCB", fallbackMethod = "fallback")
    public <T, R> Mono<R> post(String url, T requestBody, Class<R> responseType, String authToken) {
        WebClient.RequestBodySpec request = (WebClient.RequestBodySpec) webClient.post().uri(url).bodyValue(requestBody);
        if (authToken != null && !authToken.isEmpty()) {
            request = request.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken));
        }

        return request.retrieve()
                .bodyToMono(responseType);
    }

    // Generic fallback method
    private <T> Mono<T> fallback(String url, Class<T> responseType, String authToken, Throwable t) {
        return Mono.error(new RuntimeException("Service unavailable. Please try again later."));
    }

    private <T, R> Mono<R> fallback(String url, T requestBody, Class<R> responseType, String authToken, Throwable t) {
        return Mono.error(new RuntimeException("Service unavailable. Please try again later."));
    }
}
