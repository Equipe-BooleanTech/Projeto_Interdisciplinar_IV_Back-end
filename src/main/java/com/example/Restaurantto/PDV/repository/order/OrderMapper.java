package com.example.Restaurantto.PDV.repository.order;

import com.example.Restaurantto.PDV.dto.order.GetOrderDTO;
import com.example.Restaurantto.PDV.dto.order.OrderDTO;
import com.example.Restaurantto.PDV.model.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDTO orderDTO);

    GetOrderDTO toGetOrderDTO(Order order);

    void updateEntityFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}
