package com.example.Restaurantto.PDV.service.order;

import com.example.Restaurantto.PDV.dto.order.GetOrderDTO;
import com.example.Restaurantto.PDV.dto.order.OrderDTO;
import com.example.Restaurantto.PDV.exception.order.OrderNotFoundException;
import com.example.Restaurantto.PDV.mapper.OrderMapper;
import com.example.Restaurantto.PDV.model.order.Order;
import com.example.Restaurantto.PDV.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public UUID createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return order.getId();
    }

    public void updateOrder(UUID id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

        orderMapper.updateEntityFromDTO(orderDTO, order);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Pedido não encontrado");
        }
        orderRepository.deleteById(id);
    }

    public Page<GetOrderDTO> listAllOrders(PageRequest pageRequest) {
        return orderRepository.findAll(pageRequest)
                .map(orderMapper::toGetOrderDTO);
    }

    public GetOrderDTO getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
        return orderMapper.toGetOrderDTO(order);
    }
}
