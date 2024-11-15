package com.example.Restaurantto.PDV.dto.order;

import com.example.Restaurantto.PDV.enums.OrderStatus;

public record OrderDTO(
        String customerName,
        OrderStatus status,
        Double totalAmount
) {
}
