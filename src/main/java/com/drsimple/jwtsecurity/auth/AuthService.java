package com.drsimple.jwtsecurity.auth;


import com.drsimple.jwtsecurity.dto.LoginRequest;
import com.drsimple.jwtsecurity.dto.RefreshTokenRequest;
import com.drsimple.jwtsecurity.dto.RegisterRequest;
import com.drsimple.jwtsecurity.dto.TokenPair;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import com.drsimple.jwtsecurity.service.JwtService;
import com.drsimple.jwtsecurity.util.EmailService;
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

    @Transactional
    public void registerUser(RegisterRequest registerRequest) throws MessagingException {
        // Check if user with the same username already exist
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        // Create new user
        User user = User
                .builder()
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        userRepository.save(user);

        Map<String, Object> model = new HashMap<>();
        model.put("name", "John Doe");
        model.put("verificationUrl", "https://example.com/verify?token=abc123");

        emailService.sendVerificationEmail(
                "abayomiogunnusi@gmail.com",
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
