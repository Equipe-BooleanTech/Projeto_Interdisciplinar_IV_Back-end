package com.example.Restaurantto.PDV.repository.groupSheet;

import com.example.Restaurantto.PDV.model.groupSheet.GroupSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupSheetRepository extends JpaRepository<GroupSheet, UUID> {
    Optional<GroupSheet> findByName(String name);
}
