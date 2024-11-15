package com.example.Restaurantto.PDV.mapper.groupSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.DataSheetDTO;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DataSheetMapper {
    DataSheetMapper INSTANCE = Mappers.getMapper(DataSheetMapper.class);

    DataSheetDTO toDataSheetDTO(DataSheet dataSheet);
    DataSheet toDataSheet(DataSheetDTO dataSheetDTO);
}
