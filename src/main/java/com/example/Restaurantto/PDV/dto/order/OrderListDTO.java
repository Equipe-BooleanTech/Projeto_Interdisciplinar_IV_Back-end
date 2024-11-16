package com.example.Restaurantto.PDV.dto.order;

import com.example.Restaurantto.PDV.enums.OrderStatus;

import java.util.UUID;

public record OrderListDTO(
        UUID id,
        String customerName,
        OrderStatus status
) {
}
