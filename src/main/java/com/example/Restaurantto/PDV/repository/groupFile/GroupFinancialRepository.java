package com.example.Restaurantto.PDV.repository.groupFile;

import com.example.Restaurantto.PDV.model.groupFile.GroupFinancial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupFinancialRepository extends JpaRepository<GroupFinancial, UUID> {
    Optional<GroupFinancial> findByName(String name);
    List<GroupFinancial> findAllByCreatedAtBetween(LocalDate start, LocalDate end);
}
