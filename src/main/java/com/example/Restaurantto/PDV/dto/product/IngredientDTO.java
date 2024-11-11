package com.example.Restaurantto.PDV.dto.product;

import com.example.Restaurantto.PDV.model.product.Supplier;


import java.time.LocalDate;
import java.util.Set;

public record IngredientDTO(String name,
                            Set<Supplier> supplier,
                            Double price,
                            String unit,
                            Double quantity,
                            String description,
                            Boolean isAnimalOrigin,
                            String sif,
                            LocalDate createdAt
                         ) {
}
