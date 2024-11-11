package com.example.Restaurantto.PDV.repository.groupSheet;

import com.example.Restaurantto.PDV.model.dataSheet.DataSheet;
import com.example.Restaurantto.PDV.model.groupSheet.GroupSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupSheetRepository extends JpaRepository<GroupSheet, UUID> {
    Optional<GroupSheet> findByName(String name);
    List<GroupSheet> findAllByCreatedAtBetween(LocalDate start, LocalDate end);

}
