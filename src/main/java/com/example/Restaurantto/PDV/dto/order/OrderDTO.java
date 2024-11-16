package com.example.Restaurantto.PDV.dto.order;

import com.example.Restaurantto.PDV.enums.OrderStatus;

import java.math.BigDecimal;

public record OrderDTO(
        String customerName,
        OrderStatus status,
        BigDecimal totalAmount
) {
}
