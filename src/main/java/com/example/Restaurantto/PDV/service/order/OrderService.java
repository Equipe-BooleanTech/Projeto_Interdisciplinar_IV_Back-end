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

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public UUID createOrder(OrderDTO orderDTO) {
        Order order = Order.builder()
                .customerName(orderDTO.customerName())
                .status(orderDTO.status())
                .createdAt(LocalDate.now())
                .build();
        orderRepository.save(order);
        return order.getId();
    }

    public void updateOrder(UUID id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

        order.setCustomerName(orderDTO.customerName());
        order.setStatus(orderDTO.status());
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
                .map(OrderMapper.INSTANCE::toOrder);
    }

    public Optional<Order> getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));
        return orderRepository.findById(id);
    }
}
