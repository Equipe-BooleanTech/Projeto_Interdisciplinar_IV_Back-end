package com.example.Restaurantto.PDV.service.dataSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.GetDataSheetDTO;
import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetNotFoundException;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.product.IngredientNotFoundException;
import com.example.Restaurantto.PDV.mapper.dataSheet.DataSheetMapper;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.repository.dataSheet.DataSheetRepository;
import com.example.Restaurantto.PDV.repository.product.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataSheetService {


    private final DataSheetRepository dataSheetRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public DataSheetService(DataSheetRepository dataSheetRepository, IngredientRepository ingredientRepository) {
        this.dataSheetRepository = dataSheetRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public UUID salvarDataSheet(DataSheetDTO dataSheetDTO) {
        if (dataSheetRepository.findByName(dataSheetDTO.name()).isPresent()) {
            throw new DataSheetAlreadyRegisteredException("Ficha técnica já cadastrada");
        }

        Set<Ingredient> ingredients = dataSheetDTO.ingredients().stream()
                .map(ingredient -> ingredientRepository.findByName(ingredient.name())
                        .orElseThrow(() -> new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO: " + ingredient.name())))
                .collect(Collectors.toSet());

        // Cálculo do custo total com base nos ingredientes
        double cost = ingredients.stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();

        // Cálculo do preço de venda (30% acima do custo)
        double salePrice = cost * 1.3;

        DataSheet dataSheet = DataSheet.builder()
                .name(dataSheetDTO.name())
                .unit(dataSheetDTO.unit())
                .ingredients(ingredients)
                .cost(cost)
                .salePrice(salePrice)
                .createdAt(LocalDate.now())
                .build();

        dataSheetRepository.save(dataSheet);
        return dataSheet.getId();
    }


    public void atualizarDataSheet(UUID id, DataSheetDTO dataSheetDTO) {
        DataSheet dataSheet = dataSheetRepository.findById(id)
                .orElseThrow(() -> new DataSheetNotFoundException("Ficha técnica não encontrada"));

        Set<Ingredient> ingredients = dataSheetDTO.ingredients().stream()
                .map(ingredient -> ingredientRepository.findByName(ingredient.name())
                        .orElseThrow(() -> new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO: " + ingredient.name())))
                .collect(Collectors.toSet());

        // Atualizar os campos
        dataSheet.setName(dataSheetDTO.name());
        dataSheet.setUnit(dataSheetDTO.unit());
        dataSheet.setIngredients(ingredients);

        // Recalcular os valores
        double cost = ingredients.stream()
                .mapToDouble(Ingredient::getPrice)
                .sum();
        double salePrice = cost * 1.3;

        dataSheet.setCost(cost);
        dataSheet.setSalePrice(salePrice);

        dataSheetRepository.save(dataSheet);
    }

    public void deletarDataSheet(UUID id) {
        if (!dataSheetRepository.existsById(id)) {
            throw new DataSheetNotFoundException("Ficha técnica não encontrada");
        }
        dataSheetRepository.deleteById(id);
    }

    // Listar todas as fichas técnicas
    public Page<GetDataSheetDTO> listarTodosDataSheets(PageRequest pageRequest) {
        return dataSheetRepository.findAll(pageRequest)
                .map(DataSheetMapper.INSTANCE::toDataSheetDTO);
    }


    public Optional<DataSheet> listarFichaPeloId(UUID id) {
        return dataSheetRepository.findById(id);
    }


        public TimeSummaryDTO listarFichasPorPeriodo(DateRangeDTO dateRangeDTO) {
            List<DataSheet> data = dataSheetRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

            List<GetDataSheetDTO> dataSheetDTOList = data.stream()
                    .map(DataSheetMapper.INSTANCE::toDataSheetDTO)
                    .toList();

            return new TimeSummaryDTO(Collections.singletonList(dataSheetDTOList), dataSheetDTOList.size());
        }
    }

