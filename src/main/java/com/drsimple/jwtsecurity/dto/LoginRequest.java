package com.drsimple.jwtsecurity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username is required")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "example@email.com")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
