package com.example.Restaurantto.PDV.dto.groupFile;

import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;

import java.time.LocalDate;
import java.util.Set;

public record GroupFinancialDTO(String name,
                                Set<RevenueDTO> revenues,
                                Set<ExpensesDTO> expenses,
                                LocalDate createdAt) {
}
