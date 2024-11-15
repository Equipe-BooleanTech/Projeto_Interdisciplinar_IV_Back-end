package com.example.Restaurantto.PDV.dto.groupFile;


import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetGroupFinancialDTO(
        UUID id,
        String name,
        List<RevenueDTO> revenues,
        List<ExpensesDTO> expenses,
        LocalDate createdAt
) {
}
