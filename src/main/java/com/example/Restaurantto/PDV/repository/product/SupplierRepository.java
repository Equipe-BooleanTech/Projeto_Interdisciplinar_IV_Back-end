package com.example.Restaurantto.PDV.repository.product;

import com.example.Restaurantto.PDV.model.product.Ingredient;
import com.example.Restaurantto.PDV.model.product.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID>{

    Optional<Supplier> findByName(String name);
    List<Supplier> findAllByCreatedAtBetween(LocalDate start, LocalDate end);
}
