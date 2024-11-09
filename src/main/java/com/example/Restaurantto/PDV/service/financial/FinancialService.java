package com.example.Restaurantto.PDV.service.financial;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;
import com.example.Restaurantto.PDV.dto.financial.FinancialSummaryDTO;
import com.example.Restaurantto.PDV.exception.financial.NoFinancialRecordsException;
import com.example.Restaurantto.PDV.exception.financial.RecordNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class FinancialService {

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private RevenueRepository revenueRepository;


    public UUID criarDespesa(ExpensesDTO expensesDTO) {
        Expenses expense = new Expenses();
        expense.setDescription(expensesDTO.description());
        expense.setCategory(expensesDTO.category());
        expense.setAmount(expensesDTO.amount());
        expense.setPaymentDate(expensesDTO.paymentDate());

        expensesRepository.save(expense);
        return expense.getId();
    }

    public void atualizarDespesa(UUID id, ExpensesDTO expensesDTO){
        Expenses expense = expensesRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Despesa não encontrada"));

        expense.setDescription(expensesDTO.description());
        expense.setCategory(expensesDTO.category());
        expense.setAmount(expensesDTO.amount());
        expense.setPaymentDate(expensesDTO.paymentDate());

        expensesRepository.save(expense);
    }

    public void deletarDespesa(UUID id){
        if (!expensesRepository.existsById(id)) {
            throw new RecordNotFoundException("Despesa não encontrada");
        }
        expensesRepository.deleteById(id);
    }

    // CRUD para Revenue

    public UUID criarReceita(RevenueDTO revenueDTO) {
        Revenue revenue = new Revenue();
        revenue.setAmount(revenueDTO.amount());
        revenue.setSaleDate(revenueDTO.saleDate());

        revenueRepository.save(revenue);
        return revenue.getId();
    }

    public void atualizarReceita(UUID id, RevenueDTO revenueDTO){
        Revenue revenue = revenueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Receita não encontrada"));

        revenue.setAmount(revenueDTO.amount());
        revenue.setSaleDate(revenueDTO.saleDate());

        revenueRepository.save(revenue);
    }

    public void deletarReceita(UUID id){
        if (!revenueRepository.existsById(id)) {
            throw new RecordNotFoundException("Receita não encontrada");
        }
        revenueRepository.deleteById(id);
    }

    public BigDecimal calcularTotalDespesas(DateRangeDTO dateRange) {
        List<Expenses> despesas = expensesRepository.findByPaymentDateBetween(dateRange.startDate(), dateRange.endDate());
        if (despesas.isEmpty()) {
            throw new NoFinancialRecordsException("Nenhuma despesa encontrada no período especificado.");
        }
        return despesas.stream()
                .map(Expenses::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularTotalReceitas(DateRangeDTO dateRange) {
        List<Revenue> receitas = revenueRepository.findBySaleDateBetween(dateRange.startDate(), dateRange.endDate());
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
    public Optional<Expenses> listarDespesaPeloId(UUID id) {
        return expensesRepository.findById(id);
    }
    public Optional<Revenue> listarReceitaPeloId(UUID id) {
        return revenueRepository.findById(id);
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