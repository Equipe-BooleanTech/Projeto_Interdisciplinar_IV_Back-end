package com.example.Restaurantto.PDV.dto.dataSheet;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;

import java.util.List;

public record DataSheetDTO (
        String name,
        List<IngredientDTO> ingredients
){}
