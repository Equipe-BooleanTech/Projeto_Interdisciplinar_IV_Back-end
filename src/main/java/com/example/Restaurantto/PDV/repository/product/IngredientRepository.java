package com.example.Restaurantto.PDV.repository.product;

import com.example.Restaurantto.PDV.model.product.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

}
