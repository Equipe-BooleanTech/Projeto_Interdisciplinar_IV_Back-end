package com.example.Restaurantto.PDV.dto.dataSheet;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.enums.UnitType;

import java.util.List;

public record DataSheetDTO(
        String name,
        UnitType unit, // Unidade do produto
        List<IngredientDTO> ingredients
) {
}
