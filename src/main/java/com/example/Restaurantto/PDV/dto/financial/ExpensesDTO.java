package com.example.Restaurantto.PDV.dto.financial;

import com.example.Restaurantto.PDV.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpensesDTO(UUID id,
                          String description,
                          ExpenseCategory category,
                          BigDecimal amount,
                          LocalDate paymentDate) {
}
