package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.LoginRequest;
import com.drsimple.jwtsecurity.dto.RefreshTokenRequest;
import com.drsimple.jwtsecurity.dto.RegisterRequest;
import com.drsimple.jwtsecurity.dto.TokenPair;
import com.drsimple.jwtsecurity.exception.ConflictException;
import com.drsimple.jwtsecurity.exception.CustomBadRequestException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import com.drsimple.jwtsecurity.service.JwtService;
import com.drsimple.jwtsecurity.util.*;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;
    private final TokenBlacklistService tokenBlacklistService;
    private final CloudinaryService cloudinaryService;
    private final CurrentUserUtil currentUserUtil;
    private final AuditService auditService;


    public String uploadProfilePic(MultipartFile file) throws IOException {
        User loggedInUser = currentUserUtil.getLoggedInUser();
        var user = userRepository.findById(loggedInUser.getId());
        if(user.isEmpty()) throw  new CustomBadRequestException("User not logged in");

        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed.");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds 5MB.");
        }

        User theUser = user.get();
        String imageUrl = cloudinaryService.uploadFile(file);
        theUser.setProfilePic(imageUrl);

        userRepository.save(theUser);
        return imageUrl;
    }

    public void logout(String token) {
        long remainingMillis = jwtService.getRemainingValidity(token);
        tokenBlacklistService.blacklistToken(token, remainingMillis);
    }

    @Transactional
    public void registerUser(RegisterRequest registerRequest) throws MessagingException {
        // Check if user with the same username already exist
        if(userRepository.existsByUsername(registerRequest.getEmail())) {
            throw new ConflictException("Username is already in use");
        }

        // Create new user
        User user = User
                .builder()
                .createdAt(LocalDateTime.now())
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getEmail())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        userRepository.save(user);

        Map<String, Object> model = new HashMap<>();
        model.put("name", registerRequest.getFullName());
        model.put("verificationUrl", "https://example.com/verify?token=abc123");

        emailService.sendVerificationEmail(
                registerRequest.getEmail(),
                "Verify your account",
                "verification", // template name without `.html`
                model
        );
    }

    public TokenPair login(LoginRequest loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Generate Token Pair
        return jwtService.generateTokenPair(authentication);
    }

    public TokenPair refreshToken(@Valid RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        // check if it is valid refresh token
        if(!jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String user = jwtService.extractUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if (userDetails == null) {
            throw new IllegalArgumentException("User not found");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        String accessToken = jwtService.generateAccessToken(authentication);
        return new TokenPair(accessToken, refreshToken);
    }
}
