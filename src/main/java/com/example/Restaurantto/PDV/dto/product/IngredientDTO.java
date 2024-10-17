package com.example.Restaurantto.PDV.dto.product;

import com.example.Restaurantto.PDV.model.product.Supplier;


import java.util.Set;

public record IngredientDTO(String name,
                            Set<Supplier> supplier,
                            Double price,
                            String description,
                            Boolean isAnimalOrigin,
                            String sif
                         ) {
}
