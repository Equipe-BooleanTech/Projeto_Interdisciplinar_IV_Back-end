package com.example.Restaurantto.PDV.dto.dataSheet;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;

import java.util.List;
import java.util.UUID;

public record GetDataSheetDTO(
        UUID id,
        String name,
        List<IngredientDTO> ingredients
    ) {
}
