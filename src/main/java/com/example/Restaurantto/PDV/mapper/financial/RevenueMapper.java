package com.example.Restaurantto.PDV.mapper.financial;

import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;
import com.example.Restaurantto.PDV.model.financial.Revenue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RevenueMapper {
    RevenueMapper INSTANCE = Mappers.getMapper(RevenueMapper.class);

    RevenueDTO toRevenueDTO(Revenue revenue);
    Revenue toRevenue(RevenueDTO revenueDTO);

}
