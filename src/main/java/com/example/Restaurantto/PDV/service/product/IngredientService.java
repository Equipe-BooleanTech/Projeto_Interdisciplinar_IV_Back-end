package com.example.Restaurantto.PDV.service.product;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.GetIngredientDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.dto.product.TimeIngredientSummaryDTO;
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

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
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
                .map(supplier -> supplierRepository.findByName(supplier.getName())
                        .orElseThrow(() -> new SupplierNotFoundException("FORNECEDOR NÃO ENCONTRADO: " + supplier.getName())))
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

    private GetIngredientDTO listarIngrediente(Ingredient ingredient) {
        return new GetIngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getSuppliers(),
                ingredient.getPrice(),
                ingredient.getUnit(),
                ingredient.getQuantity(),
                ingredient.getDescription(),
                ingredient.getIsAnimalOrigin(),
                ingredient.getSif(),
                ingredient.getCreatedAt()
        );
    }

    public Page<GetIngredientDTO> listarTodosIngredientes(PageRequest pageRequest) {
        return ingredientRepository.findAll(pageRequest)
                .map(this::listarIngrediente);
    }

    public Optional<Ingredient> listarIngredientePeloId(UUID id) {
        return ingredientRepository.findById(id);
    }

    public Map<String, TimeIngredientSummaryDTO> listarIngredientesPorPeriodo(DateRangeDTO dateRangeDTO, String groupingType) {
        List<Ingredient> ingredients = ingredientRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        return ingredients.stream()
                .map(this::listarIngrediente)
                .collect(Collectors.groupingBy(
                        ingredient -> {
                            switch (groupingType.toLowerCase()) {
                                case "weekly":
                                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                    int weekNumber = ingredient.createdAt().get(weekFields.weekOfWeekBasedYear());
                                    int weekYear = ingredient.createdAt().getYear();
                                    return STR."Week \{weekNumber}, \{weekYear}";
                                case "yearly":
                                    int year = ingredient.createdAt().getYear();
                                    return STR."Year \{year}";
                                default:
                                    return STR."\{ingredient.createdAt()
                                            .getMonth().
                                            getDisplayName(TextStyle.FULL, Locale.getDefault())
                                            }\{ingredient.createdAt().getYear()}";
                            }
                        },
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ingredientList -> new TimeIngredientSummaryDTO(ingredientList, ingredientList.size())
                        )
                ));
    }
}
