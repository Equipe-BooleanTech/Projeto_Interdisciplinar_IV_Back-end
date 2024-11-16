package com.example.Restaurantto.PDV.dto.dataSheet;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.enums.UnitType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetDataSheetDTO(
        UUID id,
        String name,
        UnitType unit,
        Double cost,
        Double salePrice,
        List<IngredientDTO> ingredients,
        LocalDate createdAt
    ) {
}
