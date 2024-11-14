package com.example.Restaurantto.PDV.service.dataSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.GetDataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.TimeDataSheetSummaryDTO;
import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.dto.user.TimeUsersSummaryDTO;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetNotFoundException;
import com.example.Restaurantto.PDV.exception.dataSheet.DataSheetAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.product.IngredientNotFoundException;
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

    @Autowired
    private DataSheetRepository dataSheetRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public UUID salvarDataSheet(DataSheetDTO dataSheetDTO) {

        if (dataSheetRepository.findByName(dataSheetDTO.name()).isPresent()) {
            throw new DataSheetAlreadyRegisteredException("Ficha técnica já cadastrada");
        }
        Set<Ingredient> ingredients = dataSheetDTO.ingredients().stream()
                .map(ingredient -> ingredientRepository.findByName(ingredient.name())
                        .orElseThrow(() -> new IngredientNotFoundException("INGREDIENTE NÃO ENCONTRADO: " + ingredient.name())))
                .collect(Collectors.toSet());


        // Criar a ficha técnica com os ingredientes associados
        DataSheet dataSheet = DataSheet.builder()
                .name(dataSheetDTO.name())
                .ingredients(ingredients)
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

        dataSheet.setName(dataSheetDTO.name());
        dataSheet.setIngredients(ingredients);

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
                .map(this::mapearParaGetDataSheetDTO);
    }


    private Ingredient mapearParaEntidadeIngrediente(IngredientDTO dto) {
        return Ingredient.builder()
                .name(dto.name())
                .quantity(dto.quantity())
                .unit(dto.unit())
                .build();
    }

    private GetDataSheetDTO mapearParaGetDataSheetDTO(DataSheet dataSheet) {
        List<IngredientDTO> ingredients = dataSheet.getIngredients().stream()
                .map(this::mapearParaDTOIngrediente)
                .collect(Collectors.toList());

        return new GetDataSheetDTO(
                dataSheet.getId(),
                dataSheet.getName(),
                ingredients,
                dataSheet.getCreatedAt()
        );
    }

    private IngredientDTO mapearParaDTOIngrediente(Ingredient ingredient) {
        return new IngredientDTO(ingredient.getName(),
                ingredient.getSuppliers(),
                ingredient.getPrice(),
                ingredient.getUnit(),
                ingredient.getQuantity(),
                ingredient.getDescription(),
                ingredient.getIsAnimalOrigin(),
                ingredient.getSif(),
                ingredient.getCreatedAt());
    }

    public Optional<DataSheet> listarFichaPeloId(UUID id) {
        return dataSheetRepository.findById(id);
    }


        public TimeDataSheetSummaryDTO listarFichasPorPeriodo(DateRangeDTO dateRangeDTO) {
            List<DataSheet> data = dataSheetRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

            List<GetDataSheetDTO> dataSheetDTOList = data.stream()
                    .map(this::mapearParaGetDataSheetDTO)
                    .collect(Collectors.toList());

            return new TimeDataSheetSummaryDTO(dataSheetDTOList, dataSheetDTOList.size());
        }
    }

