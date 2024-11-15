package com.example.Restaurantto.PDV.repository.financial;

import com.example.Restaurantto.PDV.model.financial.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpensesRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByPaymentDateBetween(LocalDate start, LocalDate end);
    List<Expense> findAllBypaymentDateBetween(LocalDate start, LocalDate end);

}
