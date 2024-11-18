package com.example.Restaurantto.PDV.service.financial;

import com.example.Restaurantto.PDV.dto.financial.*;
import com.example.Restaurantto.PDV.dto.global.TimeSummaryDTO;
import com.example.Restaurantto.PDV.exception.financial.NoFinancialRecordsException;
import com.example.Restaurantto.PDV.exception.financial.RecordNotFoundException;
import com.example.Restaurantto.PDV.mapper.financial.ExpenseMapper;
import com.example.Restaurantto.PDV.mapper.financial.RevenueMapper;
import com.example.Restaurantto.PDV.model.financial.Expense;
import com.example.Restaurantto.PDV.model.financial.Revenue;
import com.example.Restaurantto.PDV.repository.financial.ExpensesRepository;
import com.example.Restaurantto.PDV.repository.financial.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FinancialService {


    private final ExpensesRepository expensesRepository;
    private final RevenueRepository revenueRepository;

    @Autowired
    public FinancialService(ExpensesRepository expensesRepository, RevenueRepository revenueRepository) {
        this.expensesRepository = expensesRepository;
        this.revenueRepository = revenueRepository;
    }


    public UUID criarDespesa(ExpensesDTO expensesDTO) {
        Expense expense = Expense.builder()
                        .name(expensesDTO.name())
                        .description(expensesDTO.description())
                        .category(expensesDTO.category())
                        .amount(expensesDTO.amount())
                        .paymentDate(expensesDTO.paymentDate())
                        .build();

        expensesRepository.save(expense);
        return expense.getId();
    }

    public void atualizarDespesa(UUID id, ExpensesDTO expensesDTO){
        Expense expense = expensesRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Despesa não encontrada"));
        expense.setName(expensesDTO.name());
        expense.setDescription(expensesDTO.description());
        expense.setCategory(expensesDTO.category());
        expense.setAmount(expensesDTO.amount());
        expense.setPaymentDate(expensesDTO.paymentDate());

        expensesRepository.save(expense);    }

    public void deletarDespesa(UUID id){
        if (!expensesRepository.existsById(id)) {
            throw new RecordNotFoundException("Despesa não encontrada");
        }
        expensesRepository.deleteById(id);
    }


    public UUID criarReceita(RevenueDTO revenueDTO) {
        Revenue revenue = Revenue.builder()
                        .name(revenueDTO.name())
                        .amount(revenueDTO.amount())
                        .saleDate(revenueDTO.saleDate())
                        .paymentMethod(revenueDTO.paymentMethod())
                        .category(revenueDTO.category())
                        .paymentStatus(revenueDTO.paymentStatus())
                        .employee(revenueDTO.employee())
                        .orderNumber(revenueDTO.orderNumber())
                        .build();

        revenueRepository.save(revenue);
        return revenue.getId();
    }

    public void atualizarReceita(UUID id, RevenueDTO revenueDTO){
        Revenue revenue = revenueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Receita não encontrada"));
        revenue.setName(revenueDTO.name());
        revenue.setAmount(revenueDTO.amount());
        revenue.setSaleDate(revenueDTO.saleDate());
        revenue.setPaymentMethod(revenueDTO.paymentMethod());
        revenue.setCategory(revenueDTO.category());
        revenue.setPaymentStatus(revenueDTO.paymentStatus());
        revenue.setEmployee(revenueDTO.employee());
        revenue.setOrderNumber(revenueDTO.orderNumber());

        revenueRepository.save(revenue);
    }

    public void deletarReceita(UUID id){
        if (!revenueRepository.existsById(id)) {
            throw new RecordNotFoundException("Receita não encontrada");
        }
        revenueRepository.deleteById(id);
    }

    public BigDecimal calcularTotalDespesas(DateRangeDTO dateRange) {
        List<Expense> despesas = expensesRepository.findByPaymentDateBetween(dateRange.startDate(), dateRange.endDate());
        if (despesas.isEmpty()) {
            throw new NoFinancialRecordsException("Nenhuma despesa encontrada no período especificado.");
        }
        return despesas.stream()
                .map(Expense::getAmount)
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
                .map(ExpenseMapper.INSTANCE::toExpenseDTO);
    }

    public Page<RevenueDTO> listarTodasReceitas(PageRequest pageRequest) {
        return revenueRepository.findAll(pageRequest)
                .map(RevenueMapper.INSTANCE::toRevenueDTO);
    }
    public Optional<Expense> listarDespesaPeloId(UUID id) {
        return expensesRepository.findById(id);
    }
    public Optional<Revenue> listarReceitaPeloId(UUID id) {
        return revenueRepository.findById(id);
    }


    public TimeSummaryDTO listarReceitasPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<Revenue> data = revenueRepository.findAllBysaleDateBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<RevenueDTO> revenueDTOList = data.stream()
                .map(RevenueMapper.INSTANCE::toRevenueDTO)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(revenueDTOList), revenueDTOList.size());
    }
    public TimeSummaryDTO listarDespesasPorPeriodo(DateRangeDTO dateRangeDTO) {
        List<Expense> data = expensesRepository.findAllBypaymentDateBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        List<ExpensesDTO> expensesDTOList = data.stream()
                .map(ExpenseMapper.INSTANCE::toExpenseDTO)
                .toList();

        return new TimeSummaryDTO(Collections.singletonList(expensesDTOList), expensesDTOList.size());
    }
}
