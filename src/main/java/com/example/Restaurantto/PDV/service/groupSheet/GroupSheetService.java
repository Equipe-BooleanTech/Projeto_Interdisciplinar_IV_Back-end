package com.example.Restaurantto.PDV.service.groupSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.groupSheet.GetGroupSheetDTO;
import com.example.Restaurantto.PDV.dto.groupSheet.GroupSheetDTO;
import com.example.Restaurantto.PDV.dto.product.IngredientDTO;
import com.example.Restaurantto.PDV.exception.groupSheet.GroupSheetAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.groupSheet.GroupSheetNotFoundException;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import com.example.Restaurantto.PDV.model.groupSheet.GroupSheet;
import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.repository.dataSheet.DataSheetRepository;
import com.example.Restaurantto.PDV.repository.groupSheet.GroupSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupSheetService {

    @Autowired
    private GroupSheetRepository groupSheetRepository;

    @Autowired
    private DataSheetRepository dataSheetRepository;

    public UUID salvarGroupSheet(GroupSheetDTO groupSheetDTO) {
        if (groupSheetRepository.findByName(groupSheetDTO.name()).isPresent()) {
            throw new GroupSheetAlreadyRegisteredException("Grupo de fichas técnicas já cadastrado");
        }

        // Map DataSheetDTO to DataSheet entities
        Set<DataSheet> dataSheets = groupSheetDTO.datasheets().stream()
                .map(dataSheet -> dataSheetRepository.findByName(dataSheet.name())
                        .orElseThrow(() -> new GroupSheetNotFoundException("FICHA TÉCNICA NÃO ENCONTRADA: " + dataSheet.name())))
                .collect(Collectors.toSet());

        // Create the GroupSheet with associated DataSheets
        GroupSheet groupSheet = GroupSheet.builder()
                .name(groupSheetDTO.name())
                .datasheets(dataSheets)
                .createdAt(LocalDate.now())
                .build();

        groupSheetRepository.save(groupSheet);

        return groupSheet.getId();
    }

    public void atualizarGroupSheet(UUID id, GroupSheetDTO groupSheetDTO) {
        GroupSheet groupSheet = groupSheetRepository.findById(id)
                .orElseThrow(() -> new GroupSheetNotFoundException("Grupo de fichas técnicas não encontrado"));

        // Map DataSheetDTO to DataSheet entities
        Set<DataSheet> dataSheets = groupSheetDTO.datasheets().stream()
                .map(dataSheet -> dataSheetRepository.findByName(dataSheet.name())
                        .orElseThrow(() -> new GroupSheetNotFoundException("FICHA TÉCNICA NÃO ENCONTRADA: " + dataSheet.name())))
                .collect(Collectors.toSet());

        groupSheet.setName(groupSheetDTO.name());
        groupSheet.setDatasheets(dataSheets);

        groupSheetRepository.save(groupSheet);
    }

    public void deletarGroupSheet(UUID id) {
        if (!groupSheetRepository.existsById(id)) {
            throw new GroupSheetNotFoundException("Grupo de fichas técnicas não encontrado");
        }
        groupSheetRepository.deleteById(id);
    }

    public Page<GetGroupSheetDTO> listarTodosGroupSheets(PageRequest pageRequest) {
        return groupSheetRepository.findAll(pageRequest)
                .map(this::mapearParaGetGroupSheetDTO);
    }

    private DataSheet mapearParaDataSheet(DataSheetDTO dataSheetDTO) {
        // Convert ingredients from DTO to Entity
        Set<Ingredient> ingredients = dataSheetDTO.ingredients().stream()
                .map(ingredientDTO -> mapearParaEntidadeIngrediente(ingredientDTO))
                .collect(Collectors.toSet());

        return DataSheet.builder()
                .name(dataSheetDTO.name())
                .ingredients(ingredients)
                .build();
    }

    private GetGroupSheetDTO mapearParaGetGroupSheetDTO(GroupSheet groupSheet) {
        List<DataSheetDTO> datasheets = groupSheet.getDatasheets().stream()
                .map(this::mapearParaDataSheetDTO)
                .collect(Collectors.toList());

        return new GetGroupSheetDTO(groupSheet.getId(), groupSheet.getName(), datasheets,groupSheet.getCreatedAt());
    }

    private DataSheetDTO mapearParaDataSheetDTO(DataSheet dataSheet) {
        List<IngredientDTO> ingredientDTOs = dataSheet.getIngredients().stream()
                .map(this::mapearParaDTOIngrediente)
                .collect(Collectors.toList());

        return new DataSheetDTO(dataSheet.getName(), ingredientDTOs);
    }

    private Ingredient mapearParaEntidadeIngrediente(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .name(ingredientDTO.name())
                .quantity(ingredientDTO.quantity())
                .unit(ingredientDTO.unit())
                .build();
    }

    private IngredientDTO mapearParaDTOIngrediente(Ingredient ingredient) {
        return new IngredientDTO(
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

    public Optional<GroupSheet> listarFichaPeloId(UUID id) {
        return groupSheetRepository.findById(id);
    }

    public TimeSummaryDTO listarGrupoDeFichasPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<GroupSheet> data = groupSheetRepository.findAllByCreatedAtBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<GetGroupSheetDTO> dataSheetDTOList = data.stream()
                .map(this::mapearParaGetGroupSheetDTO)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(dataSheetDTOList), dataSheetDTOList.size());
    }
}
