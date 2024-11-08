package com.example.Restaurantto.PDV.controller.financial;

import com.example.Restaurantto.PDV.dto.financial.DateRangeDTO;
import com.example.Restaurantto.PDV.dto.financial.ExpensesDTO;
import com.example.Restaurantto.PDV.dto.financial.FinancialSummaryDTO;
import com.example.Restaurantto.PDV.dto.financial.RevenueDTO;
import com.example.Restaurantto.PDV.service.financial.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/financials")
public class FinancialController {

    @Autowired
    private FinancialService financialService;


    @PostMapping("/total-expenses")
    public ResponseEntity<BigDecimal> getTotalExpenses(@RequestBody DateRangeDTO dateRangeDTO) {
        BigDecimal totalExpenses = financialService.calcularTotalDespesas(dateRangeDTO);
        return new ResponseEntity<>(totalExpenses, HttpStatus.OK);
    }


    @PostMapping("/total-revenue")
    public ResponseEntity<BigDecimal> getTotalRevenue(@RequestBody DateRangeDTO dateRangeDTO) {
        BigDecimal totalRevenue = financialService.calcularTotalReceitas(dateRangeDTO);
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }


    @PostMapping("/cash-flow")
    public ResponseEntity<FinancialSummaryDTO> getCashFlow(@RequestBody DateRangeDTO dateRangeDTO) {
        FinancialSummaryDTO financialSummary = financialService.gerarFluxoDeCaixa(dateRangeDTO);
        return new ResponseEntity<>(financialSummary, HttpStatus.OK);
    }


    @GetMapping("/expenses")
    public ResponseEntity<Page<ExpensesDTO>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ExpensesDTO> expenses = financialService.listarTodasDespesas(PageRequest.of(page, size));
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping("/revenues")
    public ResponseEntity<Page<RevenueDTO>> getAllRevenues(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RevenueDTO> revenues = financialService.listarTodasReceitas(PageRequest.of(page, size));
        return new ResponseEntity<>(revenues, HttpStatus.OK);
    }
}
