package com.example.Restaurantto.PDV.dto.product;

import java.util.List;

public record TimeIngredientSummaryDTO(
        List<GetIngredientDTO> ingredients,
        long total) {
}
