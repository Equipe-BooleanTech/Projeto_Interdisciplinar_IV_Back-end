package com.example.Restaurantto.PDV.service.product;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.product.GetIngredientDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.exception.product.IngredientNotFoundException;
import com.example.Restaurantto.PDV.exception.product.SupplierNotFoundException;
import com.example.Restaurantto.PDV.mapper.product.IngredientMapper;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.model.product.Supplier;
import com.example.Restaurantto.PDV.repository.product.IngredientRepository;
import com.example.Restaurantto.PDV.repository.product.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final SupplierRepository supplierRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, SupplierRepository supplierRepository) {
        this.ingredientRepository = ingredientRepository;
        this.supplierRepository = supplierRepository;
    }

    public UUID salvarIngrediente(IngredientDTO ingredientDTO) {

        Set<Supplier> suppliers = ingredientDTO.supplier().stream()
                .map(supplier -> supplierRepository.findByName(supplier.name())
                        .orElseThrow(() -> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO: " + supplier.name())))
                .collect(Collectors.toSet());

        Ingredient ingredient = Ingredient.builder()
                .name(ingredientDTO.name())
                .suppliers(suppliers)
                .price(ingredientDTO.price())
                .unit(ingredientDTO.unit())
                .quantity(ingredientDTO.quantity())
                .description(ingredientDTO.description())
                .isAnimalOrigin(ingredientDTO.isAnimalOrigin())
                .sif(ingredientDTO.sif())
                .createdAt(LocalDate.now())
                .build();

        ingredientRepository.save(ingredient);

        return ingredient.getId();
    }

    public void atualizarIngrediente(UUID id, IngredientDTO ingredientDTO) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(()-> new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO"));

        Set<Supplier> suppliers = ingredientDTO.supplier().stream()
                .map(supplier -> supplierRepository.findByName(supplier.name())
                        .orElseThrow(() -> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO: " + supplier.name())))
                .collect(Collectors.toSet());

        ingredient.setName(ingredientDTO.name());
        ingredient.setSuppliers(suppliers);
        ingredient.setPrice(ingredientDTO.price());
        ingredient.setUnit(ingredientDTO.unit());
        ingredient.setQuantity(ingredientDTO.quantity());
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


    public Page<GetIngredientDTO> listarTodosIngredientes(PageRequest pageRequest) {
        return ingredientRepository.findAll(pageRequest)
                .map(IngredientMapper.INSTANCE::toIngredient);
    }

    public Optional<Ingredient> listarIngredientePeloId(UUID id) {
        return ingredientRepository.findById(id);
    }

    public TimeSummaryDTO listarIngredientesPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<Ingredient> ingredients = ingredientRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<GetIngredientDTO> ingredientDTOList = ingredients.stream()
                .map(IngredientMapper.INSTANCE::toIngredient)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(ingredientDTOList), ingredientDTOList.size());
    }
}
