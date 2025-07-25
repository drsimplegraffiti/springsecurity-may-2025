package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.LoginRequest;
import com.drsimple.jwtsecurity.dto.RefreshTokenRequest;
import com.drsimple.jwtsecurity.dto.RegisterRequest;
import com.drsimple.jwtsecurity.dto.TokenPair;
import com.drsimple.jwtsecurity.util.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
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

    @PostMapping("/upload-profile-image")
    public ResponseEntity<ApiResponse<String>> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = authService.uploadProfilePic(file);
            return ResponseEntity.ok(ApiResponse.success("Image uploaded", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail("Upload failed: " + e.getMessage()));
        }
    }
}
