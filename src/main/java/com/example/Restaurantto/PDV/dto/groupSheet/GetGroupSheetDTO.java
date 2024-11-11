package com.example.Restaurantto.PDV.dto.groupSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record GetGroupSheetDTO(UUID id, String name, List<DataSheetDTO> datasheets, LocalDate createdAt) {
}
