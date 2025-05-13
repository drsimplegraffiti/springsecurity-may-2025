package com.drsimple.jwtsecurity.order;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderResponse {
    private Long id;

    private String orderNumber;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Long userId;

}
