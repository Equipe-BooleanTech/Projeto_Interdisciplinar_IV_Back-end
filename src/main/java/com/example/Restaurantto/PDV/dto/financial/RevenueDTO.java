package com.example.Restaurantto.PDV.dto.financial;



import com.example.Restaurantto.PDV.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RevenueDTO(UUID id,
                         BigDecimal amount,
                         LocalDate saleDate,
                         PaymentMethod paymentMethod,
                         String category,
                         Boolean paymentStatus,
                         String employee,
                         String orderNumber) {
}
