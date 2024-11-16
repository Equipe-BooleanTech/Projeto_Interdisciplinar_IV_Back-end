package com.example.Restaurantto.PDV.mapper;

import com.example.Restaurantto.PDV.dto.order.GetOrderDTO;
import com.example.Restaurantto.PDV.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    GetOrderDTO toOrder (Order order);
    Order toOrderDTO (GetOrderDTO getOrderDTO);
}
