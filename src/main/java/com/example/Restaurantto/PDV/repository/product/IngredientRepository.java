package com.example.Restaurantto.PDV.repository.product;

import com.example.Restaurantto.PDV.model.product.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Optional<Ingredient> findByName(String name);
    List<Ingredient> findAllByCreatedAtBetween(LocalDate start, LocalDate end);

}
