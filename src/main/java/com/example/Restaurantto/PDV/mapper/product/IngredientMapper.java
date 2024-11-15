package com.example.Restaurantto.PDV.mapper.product;

import com.example.Restaurantto.PDV.dto.product.GetIngredientDTO;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);


    GetIngredientDTO toIngredient(Ingredient ingredient);

    Ingredient toIngredientDTO(GetIngredientDTO getIngredientDTO);

}

