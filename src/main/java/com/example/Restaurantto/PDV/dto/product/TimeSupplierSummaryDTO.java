package com.example.Restaurantto.PDV.dto.product;

import java.util.List;

public record TimeSupplierSummaryDTO(List<GetSupplierDTO> suppliers,
                                     long total) {
}
