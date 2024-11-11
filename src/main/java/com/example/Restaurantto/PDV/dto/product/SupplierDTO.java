package com.example.Restaurantto.PDV.dto.product;

import java.time.LocalDate;

public record SupplierDTO(String name,
                          String cnpj,
                          String contact,
                          String phone,
                          LocalDate createdAt) {
}
