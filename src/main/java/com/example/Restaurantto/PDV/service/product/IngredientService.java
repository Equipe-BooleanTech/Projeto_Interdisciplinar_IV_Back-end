package com.example.Restaurantto.PDV.service.product;

import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.exception.product.IngredientNotFoundException;
import com.example.Restaurantto.PDV.exception.product.SupplierNotFoundException;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.model.product.Supplier;
import com.example.Restaurantto.PDV.repository.product.IngredientRepository;
import com.example.Restaurantto.PDV.repository.product.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    private SupplierRepository supplierRepository;

    public UUID salvarIngrediente(IngredientDTO ingredientDTO) {

        Set<Supplier> suppliers = ingredientDTO.supplier().stream()
                .map(supplier -> supplierRepository.findByName(supplier.getName())
                        .orElseThrow(() -> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO: " + supplier.getName())))
                .collect(Collectors.toSet());

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDTO.name())
                .suppliers(suppliers)
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
                .orElseThrow(()-> new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO"));

        Set<Supplier> suppliers = ingredientDTO.supplier().stream()
                .map(supplier -> supplierRepository.findByName(supplier.getName())
                        .orElseThrow(() -> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO: " + supplier.getName())))
                .collect(Collectors.toSet());

        ingredient.setName(ingredientDTO.name());
        ingredient.setSuppliers(suppliers);
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
