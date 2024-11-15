package com.example.Restaurantto.PDV.mapper.groupSheet;

import com.example.Restaurantto.PDV.dto.groupFile.GetGroupSheetDTO;
import com.example.Restaurantto.PDV.mapper.dataSheet.DataSheetMapper;
import com.example.Restaurantto.PDV.model.groupFile.GroupSheet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DataSheetMapper.class)
public interface GroupSheetMapper {
    GroupSheetMapper INSTANCE = Mappers.getMapper(GroupSheetMapper.class);

    GetGroupSheetDTO toGetGroupSheetDTO(GroupSheet groupSheet);
    GroupSheet toGroupSheet(GetGroupSheetDTO getGroupSheetDTO);

}
