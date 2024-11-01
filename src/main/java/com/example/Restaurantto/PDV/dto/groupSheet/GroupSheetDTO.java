package com.example.Restaurantto.PDV.dto.groupSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetListDTO;

import java.util.List;

public record GroupSheetDTO(String name, List<DataSheetDTO> datasheets) {
}
