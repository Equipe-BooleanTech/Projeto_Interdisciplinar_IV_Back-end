package com.example.Restaurantto.PDV.dto.financial;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FinancialSummaryDTO(LocalDate startDate,
                                  LocalDate endDate,
                                  BigDecimal totalRevenue,
                                  BigDecimal totalExpenses,
                                  BigDecimal finalBalance) {
}
