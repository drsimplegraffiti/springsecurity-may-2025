package com.drsimple.jwtsecurity.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderRequest {

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    // Optional: if you want to let clients specify the order status
    private String status; // Expecting values like "PENDING", "PAID", etc.

    @NotNull(message = "User ID is required")
    private Long userId;
}
