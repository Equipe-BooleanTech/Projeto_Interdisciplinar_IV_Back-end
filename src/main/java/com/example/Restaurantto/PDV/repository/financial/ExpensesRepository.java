package com.example.Restaurantto.PDV.repository.financial;

import com.example.Restaurantto.PDV.model.financial.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<Expenses, UUID> {
    List<Expenses> findByPaymentDateBetween(LocalDate start, LocalDate end);
    List<Expenses> findAllBypaymentDateBetween(LocalDate start, LocalDate end);

}
