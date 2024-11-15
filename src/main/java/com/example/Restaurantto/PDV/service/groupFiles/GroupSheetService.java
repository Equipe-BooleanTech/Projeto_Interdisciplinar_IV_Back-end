package com.example.Restaurantto.PDV.service.groupFiles;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GetGroupSheetDTO;
import com.example.Restaurantto.PDV.dto.groupFile.GroupSheetDTO;
import com.example.Restaurantto.PDV.exception.groupFile.GroupSheetAlreadyRegisteredException;
import com.example.Restaurantto.PDV.exception.groupFile.GroupSheetNotFoundException;
import com.example.Restaurantto.PDV.mapper.groupSheet.GroupSheetMapper;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import com.example.Restaurantto.PDV.model.groupFile.GroupSheet;
import com.example.Restaurantto.PDV.repository.dataSheet.DataSheetRepository;
import com.example.Restaurantto.PDV.repository.groupFile.GroupSheetRepository;
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
                .map(GroupSheetMapper.INSTANCE::toGetGroupSheetDTO);
    }


    public Optional<GroupSheet> listarFichaPeloId(UUID id) {
        return groupSheetRepository.findById(id);
    }

    public TimeSummaryDTO listarGrupoDeFichasPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<GroupSheet> data = groupSheetRepository.findAllByCreatedAtBetween(
                dateRangeDTO.startDate(), dateRangeDTO.endDate()
        );

        List<GetGroupSheetDTO> dataSheetDTOList = data.stream()
                .map(GroupSheetMapper.INSTANCE::toGetGroupSheetDTO)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(dataSheetDTOList), dataSheetDTOList.size());
    }
}
