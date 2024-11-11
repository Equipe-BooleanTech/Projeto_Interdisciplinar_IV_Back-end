package com.example.Restaurantto.PDV.dto.financial;


import java.util.List;

public record TimeRevenueSummaryDTO(List<RevenueDTO> revenue,
                                    long total) {
}
