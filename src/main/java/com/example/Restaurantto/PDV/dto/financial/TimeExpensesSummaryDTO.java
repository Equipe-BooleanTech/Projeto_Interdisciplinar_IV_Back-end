package com.example.Restaurantto.PDV.dto.financial;

import java.util.List;

public record TimeExpensesSummaryDTO(List<ExpensesDTO> expenses,
                                     long total) {
}
