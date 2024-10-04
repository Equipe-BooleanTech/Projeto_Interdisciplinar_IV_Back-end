package com.example.Restaurantto.PDV.service.product;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.exception.product.IngredientNotFoundException;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.model.product.Supplier;
import com.example.Restaurantto.PDV.repository.product.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public UUID salvarIngrediente(IngredientDTO ingredientDTO) {

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDTO.name())
                .suppliers(Set.of(Supplier.builder().name(ingredientDTO.name()).build()))
                .price(ingredientDTO.price())
                .description(ingredientDTO.description())
                .isAnimalOrigin(ingredientDTO.isAnimalOrigin())
                .sif(ingredientDTO.sif())
                .build();

        ingredientRepository.save(ingredient);

        return ingredient.getId();
    }

    public void atualizarIngrediente(UUID id, IngredientDTO ingredientDTO) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(()-> new IngredientNotFoundException("iNGREDIENTE NÃO ENCONTRADO"));

        ingredient.setName(ingredientDTO.name());
        ingredient.setSuppliers(Set.of(Supplier.builder().name(ingredientDTO.name()).build()));
        ingredient.setPrice(ingredientDTO.price());
        ingredient.setDescription(ingredientDTO.description());
        ingredient.setIsAnimalOrigin(ingredientDTO.isAnimalOrigin());
        ingredient.setSif(ingredientDTO.sif());

        ingredientRepository.save(ingredient);
    }
    public void deletarIngrediente(UUID id) {
        if (!ingredientRepository.existsById(id)) {
            throw new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO");
        }
        ingredientRepository.deleteById(id);
    }

    private IngredientDTO listarIngrediente(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getName(),
                ingredient.getSuppliers(),
                ingredient.getPrice(),
                ingredient.getDescription(),
                ingredient.getIsAnimalOrigin(),
                ingredient.getSif());
    }

    public Page<IngredientDTO> listarTodosIngredientes(PageRequest pageRequest) {
        return ingredientRepository.findAll(pageRequest)
                .map(this::listarIngrediente);
    }
}
