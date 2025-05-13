package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.TokenPair;
import com.drsimple.jwtsecurity.service.JwtService;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import com.drsimple.jwtsecurity.user.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/login/oauth2")
@Slf4j
public class GoogleAuthController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public GoogleAuthController(RestTemplate restTemplate,
                                UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                JwtService jwtService) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @GetMapping("/code/google")
    @Transactional
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        try {
            // 1. Exchange code for access token
            String tokenUrl = "https://oauth2.googleapis.com/token";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);
            params.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, request, Map.class);

            System.out.println("=========================$$$$" + tokenResponse);
            if (!tokenResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to exchange code for token");
            }

            String idToken = (String) tokenResponse.getBody().get("id_token");
            System.out.println("===========================================" + idToken);
            // 2. Get user info from id token
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (!userInfoResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token");
            }

            Map<String, Object> userInfo = userInfoResponse.getBody();
            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");

            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found in Google response.");
            }

            // 3. Check if user exists, else create
//            User user = userRepository.findByEmail(email).orElseGet(() -> {
//                User newUser = new User();
//                newUser.setEmail(email);
//                newUser.setUsername(name != null ? name : email);
//                newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
//                newUser.setRole(Role.ROLE_USER); // Set default role
//                return userRepository.save(newUser);
//            });

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(name != null ? name : email);
                newUser.setFullName(name != null ? name : "Unknown"); // <-- Add this line
                newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                newUser.setRole(Role.ROLE_USER);
                return userRepository.save(newUser);
            });

            // 4. Create Authentication
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(user.getRole().name()))
            );

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 5. Generate JWT Token Pair
            TokenPair tokenPair = jwtService.generateTokenPair(authentication);

            return ResponseEntity.ok(tokenPair);

        } catch (Exception e) {
            log.error("Error during Google OAuth callback", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Google login failed");
        }
    }
}
