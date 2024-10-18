package com.example.Restaurantto.PDV.repository.dataSheet;

import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DataSheetRepository extends JpaRepository<DataSheet, UUID> {
    Optional<DataSheet> findByName(String name);
}
