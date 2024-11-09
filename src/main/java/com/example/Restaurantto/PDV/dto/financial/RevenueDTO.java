package com.example.Restaurantto.PDV.dto.financial;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RevenueDTO(UUID id,
                         BigDecimal amount,
                         LocalDate saleDate) {
}
