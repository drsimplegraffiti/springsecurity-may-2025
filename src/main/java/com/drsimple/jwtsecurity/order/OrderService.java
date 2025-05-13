package com.drsimple.jwtsecurity.order;

import com.drsimple.jwtsecurity.exception.CustomBadRequestException;
import com.drsimple.jwtsecurity.user.User;
import com.drsimple.jwtsecurity.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomBadRequestException("User not found"));

        Order order = Order.builder()
                .orderNumber(OrderNumberGenerator.generate())
                .totalAmount(request.getTotalAmount())
                .status(request.getStatus() != null
                        ? OrderStatus.valueOf(request.getStatus().toUpperCase())
                        : OrderStatus.PENDING)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .orderNumber(savedOrder.getOrderNumber())
                .status(savedOrder.getStatus())
                .totalAmount(savedOrder.getTotalAmount())
//                .userId(savedOrder.getUser().getId())
                .userId(user.getId())
                .build();
    }


    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .status(order.getStatus())
                        .totalAmount(order.getTotalAmount())
                        .userId(order.getUser() != null ? order.getUser().getId() : null)
                        .build())
                .collect(Collectors.toList());
    }
}
