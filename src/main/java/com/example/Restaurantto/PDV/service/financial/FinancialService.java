package com.example.Restaurantto.PDV.service.financial;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;
import com.example.Restaurantto.PDV.dto.financial.FinancialSummaryDTO;
import com.example.Restaurantto.PDV.exception.financial.NoFinancialRecordsException;
import com.example.Restaurantto.PDV.model.financial.Expenses;
import com.example.Restaurantto.PDV.model.financial.Revenue;
import com.example.Restaurantto.PDV.repository.financial.ExpensesRepository;
import com.example.Restaurantto.PDV.repository.financial.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancialService {

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private RevenueRepository revenueRepository;


    public BigDecimal calcularTotalDespesas(DateRangeDTO dateRange) {
        List<Expenses> despesas = expensesRepository.findPaymentDateBetween(dateRange.startDate(), dateRange.endDate());
        if (despesas.isEmpty()) {
            throw new NoFinancialRecordsException("Nenhuma despesa encontrada no período especificado.");
        }
        return despesas.stream()
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalReceitas(DateRangeDTO dateRange) {
        List<Revenue> receitas = revenueRepository.findSaleDateBetween(dateRange.startDate(), dateRange.endDate());
        if (receitas.isEmpty()) {
            throw new NoFinancialRecordsException("Nenhuma receita encontrada no período especificado.");
        }
        return receitas.stream()
                .map(Revenue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public FinancialSummaryDTO gerarFluxoDeCaixa(DateRangeDTO dateRange) {
        BigDecimal totalReceitas = calcularTotalReceitas(dateRange);
        BigDecimal totalDespesas = calcularTotalDespesas(dateRange);
        BigDecimal saldoFinal = totalReceitas.subtract(totalDespesas);

        return new FinancialSummaryDTO(dateRange.startDate(), dateRange.endDate(), totalReceitas, totalDespesas, saldoFinal);
    }

    public Page<ExpensesDTO> listarTodasDespesas(PageRequest pageRequest) {
        return expensesRepository.findAll(pageRequest)
                .map(this::mapToExpensesDTO);
    }

    public Page<RevenueDTO> listarTodasReceitas(PageRequest pageRequest) {
        return revenueRepository.findAll(pageRequest)
                .map(this::mapToRevenueDTO);
    }

    private ExpensesDTO mapToExpensesDTO(Expenses expense) {
        return new ExpensesDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getPaymentDate()
        );
    }

    private RevenueDTO mapToRevenueDTO(Revenue revenue) {
        return new RevenueDTO(
                revenue.getId(),
                revenue.getAmount(),
                revenue.getSaleDate()
        );
    }
}
