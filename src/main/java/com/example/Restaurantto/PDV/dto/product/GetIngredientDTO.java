package com.example.Restaurantto.PDV.dto.product;

import com.example.Restaurantto.PDV.model.product.Supplier;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record GetIngredientDTO(UUID id,
                               String name,
                               Set<SupplierDTO> suppliers,
                               Double price,
                               String unit,
                               Double quantity,
                               String description,
                               Boolean isAnimalOrigin,
                               String sif,
                               LocalDate createdAt) {
}
