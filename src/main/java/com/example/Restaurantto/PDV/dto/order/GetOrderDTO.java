package com.example.Restaurantto.PDV.dto.order;

import com.example.Restaurantto.PDV.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderDTO(
        UUID id,
        String customerName,
        OrderStatus status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
