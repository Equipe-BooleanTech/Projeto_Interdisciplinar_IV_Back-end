package com.example.Restaurantto.PDV.service.financial;

import com.example.Restaurantto.PDV.dto.financial.*;
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
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

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
                .map(this::listarDespesas);
    }

    public Page<RevenueDTO> listarTodasReceitas(PageRequest pageRequest) {
        return revenueRepository.findAll(pageRequest)
                .map(this::listarReceitas);
    }
    public Optional<Expenses> listarDespesaPeloId(UUID id) {
        return expensesRepository.findById(id);
    }
    public Optional<Revenue> listarReceitaPeloId(UUID id) {
        return revenueRepository.findById(id);
    }

    private ExpensesDTO listarDespesas(Expenses expense) {
        return new ExpensesDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getPaymentDate()
        );
    }

    private RevenueDTO listarReceitas(Revenue revenue) {
        return new RevenueDTO(
                revenue.getId(),
                revenue.getAmount(),
                revenue.getSaleDate()
        );
    }

    public Map<String, TimeRevenueSummaryDTO> listarReceitasPorPeriodo(DateRangeDTO dateRangeDTO, String groupingType) {
        List<Revenue> revenues = revenueRepository.findAllBysaleDateBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        return revenues.stream()
                .map(this::listarReceitas)
                .collect(Collectors.groupingBy(
                        revenue -> {
                            switch (groupingType.toLowerCase()) {
                                case "weekly":
                                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                    int weekNumber = revenue.saleDate().get(weekFields.weekOfWeekBasedYear());
                                    int weekYear = revenue.saleDate().getYear();
                                    return STR."Week \{weekNumber}, \{weekYear}";
                                case "yearly":
                                    int year = revenue.saleDate().getYear();
                                    return STR."Year \{year}";
                                default:
                                    return STR."\{revenue.saleDate()
                                            .getMonth().
                                            getDisplayName(TextStyle.FULL, Locale.getDefault())
                                            }\{revenue.saleDate().getYear()}";
                            }
                        },
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                revenueList -> new TimeRevenueSummaryDTO(revenueList, revenueList.size())
                        )
                ));
    }

    public Map<String, TimeExpensesSummaryDTO> listarDespesasPorPeriodo(DateRangeDTO dateRangeDTO, String groupingType) {
        List<Expenses> expenses = expensesRepository.findAllBypaymentDateBetween(dateRangeDTO.startDate(), dateRangeDTO.endDate());

        return expenses.stream()
                .map(this::listarDespesas)
                .collect(Collectors.groupingBy(
                        expense -> {
                            switch (groupingType.toLowerCase()) {
                                case "weekly":
                                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                                    int weekNumber = expense.paymentDate().get(weekFields.weekOfWeekBasedYear());
                                    int weekYear = expense.paymentDate().getYear();
                                    return STR."Week \{weekNumber}, \{weekYear}";
                                case "yearly":
                                    int year = expense.paymentDate().getYear();
                                    return STR."Year \{year}";
                                default:
                                    return STR."\{expense.paymentDate()
                                            .getMonth().
                                            getDisplayName(TextStyle.FULL, Locale.getDefault())
                                            }\{expense.paymentDate().getYear()}";
                            }
                        },
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                expenseList -> new TimeExpensesSummaryDTO(expenseList, expenseList.size())
                        )
                ));
    }
}
