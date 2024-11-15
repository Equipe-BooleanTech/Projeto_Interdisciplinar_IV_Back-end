package com.example.Restaurantto.PDV.mapper.groupSheet;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO toIngredientDTO(Ingredient ingredient);
    Ingredient toIngredient(IngredientDTO ingredientDTO);
}
