package com.example.Restaurantto.PDV.dto.groupFile;

import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record GroupFinancialDTO(String name,
                                Set<RevenueDTO> revenues,
                                Set<ExpensesDTO> expenses,
                                LocalDate createdAt) {
}
