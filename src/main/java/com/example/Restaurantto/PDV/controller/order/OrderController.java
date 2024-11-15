package com.example.Restaurantto.PDV.controller.order;

import com.example.Restaurantto.PDV.dto.order.GetOrderDTO;
import com.example.Restaurantto.PDV.dto.order.OrderDTO;
import com.example.Restaurantto.PDV.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<UUID> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        UUID id = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PutMapping("/update-order/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable UUID id, @RequestBody @Valid OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-orders")
    public ResponseEntity<Page<GetOrderDTO>> listAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GetOrderDTO> orders = orderService.listAllOrders(PageRequest.of(page, size));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-order/{id}")
    public ResponseEntity<GetOrderDTO> getOrderById(@PathVariable UUID id) {
        GetOrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
