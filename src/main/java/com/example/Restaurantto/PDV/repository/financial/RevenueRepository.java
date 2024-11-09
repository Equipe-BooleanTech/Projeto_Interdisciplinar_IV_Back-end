package com.example.Restaurantto.PDV.repository.financial;

import com.example.Restaurantto.PDV.model.financial.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RevenueRepository extends JpaRepository<Revenue, UUID> {
    List<Revenue> findBySaleDateBetween(LocalDate start, LocalDate end);
}
