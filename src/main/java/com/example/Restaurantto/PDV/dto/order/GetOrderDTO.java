package com.example.Restaurantto.PDV.dto.order;

import com.example.Restaurantto.PDV.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderDTO(
        UUID id,
        String customerName,
        OrderStatus status,
        Double totalAmount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
