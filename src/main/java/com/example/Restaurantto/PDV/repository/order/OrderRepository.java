package com.example.Restaurantto.PDV.repository.order;

import com.example.Restaurantto.PDV.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findById(UUID id);
}
