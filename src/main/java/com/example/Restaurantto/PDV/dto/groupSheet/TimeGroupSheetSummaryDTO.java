package com.example.Restaurantto.PDV.dto.groupSheet;

import java.util.List;

public record TimeGroupSheetSummaryDTO(List<GetGroupSheetDTO> ingredients,
                                       long total) {
}
