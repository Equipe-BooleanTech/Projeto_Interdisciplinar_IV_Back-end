package com.example.Restaurantto.PDV.dto.groupFile;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;

import java.util.List;

public record GroupSheetDTO(String name, List<DataSheetDTO> datasheets) {
}
