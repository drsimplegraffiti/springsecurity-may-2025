package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.LoginRequest;
import com.drsimple.jwtsecurity.dto.RefreshTokenRequest;
import com.drsimple.jwtsecurity.dto.RegisterRequest;
import com.drsimple.jwtsecurity.dto.TokenPair;
import com.drsimple.jwtsecurity.util.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@Valid @RequestBody RegisterRequest request) throws MessagingException {
        // Save the new user to the database and return success response.
        authService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(tokenPair));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }
}
