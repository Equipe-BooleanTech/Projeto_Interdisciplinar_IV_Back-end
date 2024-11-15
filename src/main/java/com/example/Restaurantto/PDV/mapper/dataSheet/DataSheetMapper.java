package com.example.Restaurantto.PDV.mapper.dataSheet;

import com.example.Restaurantto.PDV.dto.dataSheet.GetDataSheetDTO;
import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface DataSheetMapper {
    DataSheetMapper INSTANCE = Mappers.getMapper(DataSheetMapper.class);

    GetDataSheetDTO toDataSheetDTO(DataSheet dataSheet);
    DataSheet toDataSheet(GetDataSheetDTO getDataSheetDTO);
}
