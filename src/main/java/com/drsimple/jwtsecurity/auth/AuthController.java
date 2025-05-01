package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.LoginRequest;
import com.drsimple.jwtsecurity.dto.RefreshTokenRequest;
import com.drsimple.jwtsecurity.dto.RegisterRequest;
import com.drsimple.jwtsecurity.dto.TokenPair;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) throws MessagingException {
        // Save the new user to the database and return success response.
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }
}
