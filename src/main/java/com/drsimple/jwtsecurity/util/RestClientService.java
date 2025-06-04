package com.drsimple.jwtsecurity.util;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestClientService {

    private final RestTemplate restTemplate;

    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders buildHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isEmpty()) {
            headers.setBearerAuth(token);
        }
        return headers;
    }

    public <T> T get(String url, Class<T> responseType, String token) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }

    public <T> T get(String url, Class<T> responseType, Map<String, ?> uriVariables, String token) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType, uriVariables);
        return response.getBody();
    }

    public <T> T post(String url, Object request, Class<T> responseType, String token) {
        HttpEntity<Object> entity = new HttpEntity<>(request, buildHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    public <T> T put(String url, Object request, Class<T> responseType, String token) {
        HttpEntity<Object> entity = new HttpEntity<>(request, buildHeaders(token));
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        return response.getBody();
    }

    public void delete(String url, String token) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public void delete(String url, Map<String, ?> uriVariables, String token) {
        HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(token));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class, uriVariables);
    }
}
