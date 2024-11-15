package com.example.Restaurantto.PDV.mapper.groupFinancial;

import com.example.Restaurantto.PDV.dto.groupFile.GetGroupFinancialDTO;
import com.example.Restaurantto.PDV.model.groupFile.GroupFinancial;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = GroupFinancialMapper.class)
public interface GroupFinancialMapper {
    GroupFinancialMapper INSTANCE = Mappers.getMapper(GroupFinancialMapper.class);

    GetGroupFinancialDTO toGetGroupFinancialDTO(GroupFinancial groupFinancial);
    GroupFinancial toGroupFinancial(GetGroupFinancialDTO getGroupFinancialDTO);
}
