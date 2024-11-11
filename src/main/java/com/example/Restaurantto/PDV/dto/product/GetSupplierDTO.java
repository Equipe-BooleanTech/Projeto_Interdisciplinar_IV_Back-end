package com.example.Restaurantto.PDV.dto.product;

import java.time.LocalDate;
import java.util.UUID;

public record GetSupplierDTO(UUID id,
                             String name,
                             String cnpj,
                             String contact,
                             String phone,
                             LocalDate createdAt) {
}
