package com.example.Restaurantto.PDV.dto.dataSheet;


import java.util.List;

public record TimeDataSheetSummaryDTO(List<GetDataSheetDTO> ingredients,
                                      long total) {
}
