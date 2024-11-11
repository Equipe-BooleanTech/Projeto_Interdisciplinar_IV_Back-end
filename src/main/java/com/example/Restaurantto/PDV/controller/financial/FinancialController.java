package com.example.Restaurantto.PDV.controller.financial;

import com.example.Restaurantto.PDV.dto.financial.*;
import com.example.Restaurantto.PDV.dto.product.TimeIngredientSummaryDTO;
import com.example.Restaurantto.PDV.exception.financial.RecordNotFoundException;
import com.example.Restaurantto.PDV.model.financial.Expenses;
import com.example.Restaurantto.PDV.model.financial.Revenue;
import com.example.Restaurantto.PDV.response.InformationResponse;
import com.example.Restaurantto.PDV.response.SuccessResponse;
import com.example.Restaurantto.PDV.service.financial.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/financials")
public class FinancialController {

    @Autowired
    private FinancialService financialService;



    @PostMapping("/create-expense")
    public ResponseEntity<SuccessResponse> criarDespesa(@RequestBody ExpensesDTO expensesDTO) {
        UUID id = financialService.criarDespesa(expensesDTO);
        SuccessResponse response = new SuccessResponse("DESPESA CRIADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/update-expense/{id}")
    public ResponseEntity<SuccessResponse> atualizarDespesa(@PathVariable UUID id, @RequestBody ExpensesDTO expensesDTO) throws RecordNotFoundException {
        financialService.atualizarDespesa(id, expensesDTO);
        SuccessResponse response = new SuccessResponse("DESPESA ATUALIZADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/delete-expense/{id}")
    public ResponseEntity<SuccessResponse> deletarDespesa(@PathVariable UUID id) throws RecordNotFoundException {
        financialService.deletarDespesa(id);
        SuccessResponse response = new SuccessResponse("DESPESA DELETADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/create-revenue")
    public ResponseEntity<SuccessResponse> criarReceita(@RequestBody RevenueDTO revenueDTO) {
        UUID id = financialService.criarReceita(revenueDTO);
        SuccessResponse response = new SuccessResponse("RECEITA CRIADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update-revenue/{id}")
    public ResponseEntity<SuccessResponse> atualizarReceita(@PathVariable UUID id, @RequestBody RevenueDTO revenueDTO) throws RecordNotFoundException {
        financialService.atualizarReceita(id, revenueDTO);
        SuccessResponse response = new SuccessResponse("RECEITA ATUALIZADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/delete-revenue/{id}")
    public ResponseEntity<SuccessResponse> deletarReceita(@PathVariable UUID id) throws RecordNotFoundException {
        financialService.deletarReceita(id);
        SuccessResponse response = new SuccessResponse("RECEITA DELETADA COM SUCESSO!", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/total-expenses")
    public ResponseEntity<InformationResponse> getTotalExpenses(@RequestBody DateRangeDTO dateRangeDTO) {
        BigDecimal totalExpenses = financialService.calcularTotalDespesas(dateRangeDTO);
        InformationResponse response = new InformationResponse("Total de Despesas",totalExpenses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/total-revenue")
    public ResponseEntity<InformationResponse> getTotalRevenue(@RequestBody DateRangeDTO dateRangeDTO) {
        BigDecimal totalRevenue = financialService.calcularTotalReceitas(dateRangeDTO);
        InformationResponse response = new InformationResponse("Total de Receitas",totalRevenue);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/cash-flow")
    public ResponseEntity<FinancialSummaryDTO> getCashFlow(@RequestBody DateRangeDTO dateRangeDTO) {
        FinancialSummaryDTO financialSummary = financialService.gerarFluxoDeCaixa(dateRangeDTO);
        return new ResponseEntity<>(financialSummary, HttpStatus.OK);
    }


    @GetMapping("/get-all-expenses")
    public ResponseEntity<Page<ExpensesDTO>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ExpensesDTO> expenses = financialService.listarTodasDespesas(PageRequest.of(page, size));
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping("/get-all-revenues")
    public ResponseEntity<Page<RevenueDTO>> getAllRevenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RevenueDTO> revenues = financialService.listarTodasReceitas(PageRequest.of(page, size));
        return new ResponseEntity<>(revenues, HttpStatus.OK);
    }

    @GetMapping("/get-expense-by-id/{id}")
    public ResponseEntity<?> buscarDespesaPorId(@PathVariable UUID id) {
        try{
            Optional<Expenses> expense = financialService.listarDespesaPeloId(id);

            if (expense.isPresent()) {
                return ResponseEntity.ok(expense);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("DESPESA NÃO ENCONTRADA");
            }}catch (RecordNotFoundException e){
            throw e;
        }
    }
    @GetMapping("/get-revenue-by-id/{id}")
    public ResponseEntity<?> buscarReceitaPorId(@PathVariable UUID id) {
        try{
            Optional<Revenue> revenue = financialService.listarReceitaPeloId(id);

            if (revenue.isPresent()) {
                return ResponseEntity.ok(revenue);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("RECEITA NÃO ENCONTRADA");
            }}catch (RecordNotFoundException e){
            throw e;
        }
    }

    @PostMapping("/list-revenues-by-period")
    public ResponseEntity<?> listarReceitasPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        // ATENÇÃO SE PASSAR A URL NORMAL ELE VAI LISTAR POR MÊS
        // PASSANDO A URL ASSIM list-expenses-by-period?groupingType=weekly ELE LISTA POR SEMANA
        // PASSANDO A URL ASSIM list-expenses-by-period?groupingType=yearly ELE LISTA POR ANO
        Map<String, TimeRevenueSummaryDTO> receitasPorPeriodo = financialService.listarReceitasPorPeriodo(dateRangeDTO, groupingType);

        if (receitasPorPeriodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUMA RECEITA ENCONTRADA NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(receitasPorPeriodo);
        }
    }

    @PostMapping("/list-expenses-by-period")
    public ResponseEntity<?> listarDespesasPorPeriodo(
            @RequestBody DateRangeDTO dateRangeDTO,
            @RequestParam(defaultValue = "monthly") String groupingType) {
        // ATENÇÃO SE PASSAR A URL NORMAL ELE VAI LISTAR POR MÊS
        // PASSANDO A URL ASSIM list-expenses-by-period?groupingType=weekly ELE LISTA POR SEMANA
        // PASSANDO A URL ASSIM list-expenses-by-period?groupingType=yearly ELE LISTA POR ANO
        Map<String, TimeExpensesSummaryDTO> despesasPorPeriodo = financialService.listarDespesasPorPeriodo(dateRangeDTO, groupingType);

        if (despesasPorPeriodo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("NENHUMA DESPESA ENCONTRADA NO PERÍODO ESPECIFICADO");
        } else {
            return ResponseEntity.ok(despesasPorPeriodo);
        }
    }

}
